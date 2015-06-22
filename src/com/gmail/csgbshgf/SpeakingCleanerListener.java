package com.gmail.csgbshgf;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

@SuppressWarnings("deprecation")
public class SpeakingCleanerListener implements Listener {
	Map<String, String> map = new HashMap<String, String>();
	Map<String, Integer> time = new HashMap<String, Integer>();

	// 监听器
	@EventHandler
	public void onChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		String s = e.getMessage();
		SC(p, s);
		Spam(p, s);
	}

	public void Spam(Player p, String s) {
		// 判断刷屏
		if (Main.isNoSpamEnable == true) {
			String pname = p.getName();
			if (map.get(pname) == null) {
				map.put(pname, s);
			} else {
				if (map.get(pname).equals(s)) {
					if (time.get(pname) == null) {
						time.put(pname, 0);
					}
					int nowtime = time.get(pname);
					time.put(pname, nowtime + 1);
					if (time.get(pname) >= Main.kicktime) {
						p.kickPlayer("刷屏踢出！");
						time.put(pname, 0);
						map.put(pname, null);
					}
					map.put(pname, s);
				}
			}

		}
	}

	public void SC(Player p, String s) {
		// 判断违禁词语
		if (Main.isSpeakingCleanerEnable == true) {
			for (int i = 0; i < Main.Word.length; i++) {
				if (s.indexOf(Main.Word[i]) >= 0) {
					p.kickPlayer("违禁词语！");
				}
			}
		}

	}
}