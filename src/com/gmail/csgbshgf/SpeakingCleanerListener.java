package com.gmail.csgbshgf;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

@SuppressWarnings("deprecation")
public class SpeakingCleanerListener implements Listener {
	Player p1, p2;
	String s1, s2;

	@EventHandler
	public void onChat(PlayerChatEvent e) {
		System.out.print(s1);
		if (p1 == null) {
			p1 = e.getPlayer();
			s1 = e.getMessage();
		} else {
			p2 = e.getPlayer();
			s2 = e.getMessage();
			System.out.print(s2);
			Judgement.onJudgement(p1, p2, s1, s2);
		}
	}
}