package Music;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class PlayerManager {

	private static PlayerManager INSTANCE;
	private final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;
	
	//Constructor
	private PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}
	
	//Method - get guild
	public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
		
		long guildId = guild.getIdLong();
		GuildMusicManager musicManager = musicManagers.get(guildId);
		
		if(musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildId, musicManager);
		}
		
		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
		
		return musicManager;
	}
	
	//Method - load & play
	public void loadAndPlay (TextChannel channel, String trackURL) {
		
		GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
		
		playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				channel.sendMessage("Adding to queue " + track.getInfo().title).queue();
				play(musicManager, track);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();
				
				if(firstTrack == null) {
					firstTrack = playlist.getTracks().get(0);
				}
				
				channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
				play(musicManager, firstTrack);
				
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				channel.sendMessage("Could not play: " + exception.getMessage()).queue();	
			}

			@Override
			public void noMatches() {
				channel.sendMessage("Nothing found by " + trackURL).queue();
			}
		});
	}
	
	//method - play
	private void play(GuildMusicManager musicManager, AudioTrack track) {
		
		musicManager.scheduler.queue(track);
		
	}
	
	//Not sure how it works. No touching
	public static synchronized PlayerManager getInstance() {
		
		if(INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}
		return INSTANCE;
	}
	
}
