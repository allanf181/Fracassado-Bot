package me.d4rk.fracassadobot;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import me.d4rk.fracassadobot.handlers.DataHandler;
import me.d4rk.fracassadobot.listeners.GuildMessageListener;
import me.d4rk.fracassadobot.listeners.GuildReactionListener;
import me.d4rk.fracassadobot.utils.Config;
import me.d4rk.fracassadobot.utils.command.CommandRegistry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;


public class Bot {

    public static JDA jda;

    public static void main(String[] args) {

        DataHandler.connect("localhost", 28015);
        CommandRegistry.registerCmds();
        Config.loadConfig();

        try {
            jda = new JDABuilder()
                    .setToken(Config.token)
                    .addEventListeners(new GuildMessageListener(), new GuildReactionListener())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(Config.isStreaming) {
            jda.getPresence().setPresence(Activity.streaming(Config.defaultPlaying, "https://twitch.tv/"), false);
        }else {
            jda.getPresence().setPresence(Activity.playing(Config.defaultPlaying), false);
        }


    }

}
