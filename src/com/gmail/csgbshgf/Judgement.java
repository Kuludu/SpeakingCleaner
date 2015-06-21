package com.gmail.csgbshgf;

import org.bukkit.entity.Player;

public class Judgement extends Main {
	public static void onJudgement(Player p1, Player p2, String s1, String s2) {
		// 判断刷屏
		if (isNoSpamEnable == true) {
			if (p1 == p2 && s1 == s2) {

			}
		}
		// 判断违禁词语
		if (isSpeakingCleanerEnable == true) {
			for (int i = 0; i < Word.length; i++) {
				if (s1.indexOf(Word[i]) >= 0) {
					p1.kickPlayer("违禁词语！");
				}
			}
		}
	}
}