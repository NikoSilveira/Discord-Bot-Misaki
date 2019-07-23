package Commands;

import java.util.List;

import Outfasted.Misaki.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageCommands extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		//Avoid responding to other bots
		if (event.getAuthor().isBot()) {
			return;
		}  
		
		
		//COMMANDS
		
		if(args[0].equalsIgnoreCase(Main.prefix + "ara")) {				//Ara Ara
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Ara Ara " + event.getAuthor().getName() + " ⭐").queue();
			
		}
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "talk")) {		//Talk through bot
			
			args[0] = "";	//Ignore command in String
			
			if(args[1].equalsIgnoreCase("del")) {	
				
				//Delete original message
				event.getMessage().delete().queue();
				args[1] = "";
				
			}
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("" + String.join(" ",args)).queue();
		}
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "f")) {			//F in the chat
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("F in the chat").queue();;
			
		}
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "announce")) {	//Announcement
			
			args[0] = "";
			
			event.getChannel().sendTyping().queue();
			event.getMessage().delete().queue();
			event.getChannel().sendMessage("@everyone, "+ event.getAuthor().getName() + " has an announcement: " + String.join(" ",args)).queue();
		}
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "call")) {		//Call everyone
			
			event.getChannel().sendTyping().queue();
			event.getMessage().delete().queue();;
			event.getChannel().sendMessage("@everyone, "+ event.getAuthor().getName() + " is summoning you!").queue();
			
		}
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "del")) {		//delete last n messages
			
			if(args.length == 1) {
				event.getChannel().sendMessage("You need to tell me how many messages you want to delete (1-100)!").queue();;
			}
			else {
				
				if(Integer.parseInt(args[1]) > 100) {
					//Limit user to 100 deleted messages
					event.getChannel().sendMessage("Too many messages! 100 is the limit, baka").queue();
				}
				else {
					
					try {
						List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
						event.getChannel().deleteMessages(messages).queue();
						
						//Embed with deletion info
						EmbedBuilder delEmbed = new EmbedBuilder();
						delEmbed.setTitle(args[1] + " messages deleted");
						delEmbed.setColor(0xe8c205);
						delEmbed.setFooter("" + event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl());
						event.getChannel().sendMessage(delEmbed.build()).queue();
						delEmbed.clear();
						
					} catch(IllegalArgumentException e) {
						//Limit user to messages no older than 2 weeks
						event.getChannel().sendMessage("You are trying to delete messages that are over 2 weeks old. Bots can't do that!").queue();
					}

				}
			}
		}
	}
}
