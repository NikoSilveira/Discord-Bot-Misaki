package Outfasted.Misaki;

import java.util.Random;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		//RANDOMIZER
		Random rand = new Random();
		
		
		//COMMANDS
		
		if(args[0].equalsIgnoreCase(Main.prefix + "commands")) {			//Display embed with list of commands
			
			EmbedBuilder commands = new EmbedBuilder();
			commands.setTitle("What do you need? ⭐\n");
			commands.setDescription
			("**--ara:** misaki sends a small greet to you\n"
				+ "**--commands**: display misaki's list of commands\n"
				+ "**--misaki:** display bot info\n"
				+ "**--coin:** throw a coin for heads or tails\n"
				+ "**--dice:** throw a dice and get a number from 1 to 6\n");
			
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
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "ara")) {			//Ara Ara
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Ara Ara " + event.getAuthor().getName() + " ⭐").queue();
			
		}
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "ping")) {			//Show latency
			
			event.getChannel().sendTyping().queue();
			long ping = event.getJDA().getPing();
			
			EmbedBuilder pingEmbed = new EmbedBuilder();
			pingEmbed.setTitle("Your ping is: ");
			pingEmbed.setDescription(""+ping+" ms");
			
			if(ping <= 90) {
				//Good lat - green
				pingEmbed.setColor(0x05ab08);
			}
			else if(ping > 100 && ping <= 150) {
				//regular lat - yellow
				pingEmbed.setColor(0xe8c205);
			}
			else if(ping > 190) {
				//bad lat - red
				pingEmbed.setColor(0xfa0505);
			}
			
			event.getChannel().sendMessage(pingEmbed.build()).queue();
			pingEmbed.clear();
			
		}
		
		/*else if(args[0].equalsIgnoreCase(Main.prefix + "add")) {
			if(args[1] != null && args[2] != null) {
				
			}
		}*/
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "coin")) {		//random of 2
			
			int number = rand.nextInt(2);
			
			if(number == 0) {
				event.getChannel().sendMessage("Heads!").queue();
			}else if(number == 1) {
				event.getChannel().sendMessage("Tails!").queue();
			}
			
		}
		
		else if(args[0].equalsIgnoreCase(Main.prefix + "dice")) {		//random of 6
			
			int number = rand.nextInt(6);
			
			if(number == 0) {
				event.getChannel().sendMessage("1").queue();
			}else if(number == 1) {
				event.getChannel().sendMessage("2").queue();
			}else if(number == 2) {
				event.getChannel().sendMessage("3").queue();
			}else if(number == 3) {
				event.getChannel().sendMessage("4").queue();
			}else if(number == 4) {
				event.getChannel().sendMessage("5").queue();
			}else if(number == 5) {
				event.getChannel().sendMessage("6").queue();
			}
		}
		
		
	}
}