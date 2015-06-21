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
		if (p2 == null) {
			p1 = e.getPlayer();
			s1 = e.getMessage();
			SC(p1, s1);
		} else {
			p2 = e.getPlayer();
			s2 = e.getMessage();
			Spam(p1, p2, s1, s2);
		}
	}

	private void Spam(Player p1, Player p2, String s1, String s2) {
		// 判断刷屏
		if (Main.isNoSpamEnable == true) {
			if (p1 == p2 && s1 == s2) {
				
			}
		}
	}

	public void SC(Player p1, String s1) {
		// 判断违禁词语
		if (Main.isSpeakingCleanerEnable == true) {
			for (int i = 0; i < Main.Word.length; i++) {
				if (s1.indexOf(Main.Word[i]) >= 0) {
					p1.kickPlayer("违禁词语！");
				}
			}
		}
	}
}