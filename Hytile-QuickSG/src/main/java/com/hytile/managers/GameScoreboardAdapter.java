package com.hytile.managers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.hytile.game.GameStates;
import com.hytile.utils.ColorUtils;

import dev.mqzen.boards.base.BoardAdapter;
import dev.mqzen.boards.base.Title;
import dev.mqzen.boards.body.Body;
import lombok.NonNull;

public class GameScoreboardAdapter implements BoardAdapter {

    private GameStates gameState;

    public GameScoreboardAdapter(GameStates gameState) {
        this.gameState = gameState;
    }


	@Override
    public @NonNull Title title(Player player) {
        String titleText;
        switch (gameState) {
            case LOBBY:
                titleText = ColorUtils.translatedMessage("&f&LQUICK&e&lSG &aBETA");
                break;
            case INGAME:
                titleText = ColorUtils.translatedMessage("&f&LQUICK&e&lSG &aBETA");
                break;
            case DEATHMATCH:
                titleText = ColorUtils.translatedMessage("&f&LQUICK&e&lSG &aBETA");
                break;
            case PREPAREROUND:
                titleText = ColorUtils.translatedMessage("&f&LQUICK&e&lSG &aBETA");
                break;
            default:
                titleText = ColorUtils.translatedMessage("&f&LQUICK&e&lSG &aBETA");
        }
        return Title.builder()
                .withText(titleText)
                .build();
    }

    @Override
    public @NonNull Body getBody(Player player) {
        Body body = Body.of(ChatColor.GRAY + "");
        switch (gameState) {
            case LOBBY:
                body.addNewLine(ColorUtils.translatedMessage( "&bKills"));
                body.addNewLine(ColorUtils.translatedMessage( "&8» &70"));
                body.addNewLine(ColorUtils.translatedMessage( ""));
                body.addNewLine(ColorUtils.translatedMessage( "&cGamesPlayed"));
                body.addNewLine(ColorUtils.translatedMessage( "&8» &70"));
                body.addNewLine(ColorUtils.translatedMessage( ""));
                body.addNewLine(ColorUtils.translatedMessage( "&aPoints "));
                body.addNewLine(ColorUtils.translatedMessage( "&8» &70"));
                body.addNewLine(ColorUtils.translatedMessage( ""));
                break;
            case INGAME:
                body.addNewLine(ChatColor.YELLOW + "Kills: " + ChatColor.GREEN + "10");
                body.addNewLine(ChatColor.YELLOW + "Deaths: " + ChatColor.RED + "5");
                break;
            case DEATHMATCH:
                body.addNewLine(ChatColor.YELLOW + "Players Alive: " + ChatColor.RED + "3");
                body.addNewLine(ChatColor.YELLOW + "Time Left: " + ChatColor.RED + "01:30");
                break;
            case PREPAREROUND:
                body.addNewLine(ChatColor.YELLOW + "Map » Revolution");
                body.addNewLine(ChatColor.GREEN + "Game Time » 30");
                break;
            default:
                body.addNewLine(ChatColor.YELLOW + "Game Status: " + ChatColor.RED + "Unknown");
        }
        body.addNewLine(ColorUtils.translatedMessage( "&eplay.hytile.com"));
        return body;
    }

}
