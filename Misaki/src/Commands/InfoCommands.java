package Commands;

import Outfasted.Misaki.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class InfoCommands extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		//No response to other bots
		if (event.getAuthor().isBot()) {
			return;
		}  
		
		
		//COMMANDS
		
		if(args[0].equalsIgnoreCase(Main.prefix + "commands")) {			//Display embed with list of commands
			
			EmbedBuilder commands = new EmbedBuilder();
			commands.setTitle("What do you need? ⭐\n");
			commands.setDescription
				( "\n*Basic commands*\n"
				+ "**--ara:** misaki sends a small greet to you\n"
				+ "**--commands**: display misaki's list of commands\n"
				+ "**--invite:** generate invite link to server\n"
				+ "**--misaki:** display bot info\n"
				+ "**--ping:** display user's latency\n"
				+ "\n*Random output commands*\n"
				+ "**--coin:** throw a coin for heads or tails\n"
				+ "**--dice:** throw a dice and get a number from 1 to 6\n"
				+ "\n*Chat filter commands*\n"
				+ "**--filter status:** check if the filter is enabled/disabled\n"
				+ "**--filter toggle:** enable/disable chat filter\n");
			
			commands.setColor(0xe8c205);
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(commands.build()).queue();
			
			commands.clear();
			
		}
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "misaki")) {			//Bot bio embed
			
			EmbedBuilder misaki = new EmbedBuilder();
			misaki.setTitle("Shokuhou Misaki desu! ⭐");
			misaki.setDescription("Description");
			misaki.addField("text","text",false);
			misaki.setColor(0xe8c205);
			misaki.setFooter("Created by OutFasted", event.getMember().getUser().getAvatarUrl());
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(misaki.build()).queue();
			
			misaki.clear();
			
		}
		
	}
}