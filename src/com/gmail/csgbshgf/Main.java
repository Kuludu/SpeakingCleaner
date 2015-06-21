package com.gmail.csgbshgf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	static String[] Word = new String[1024];
	static boolean isNoSpamEnable = true;
	static boolean isSpeakingCleanerEnable = true;
	static int kicktime;

	// 命令
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String args[]) {
		// 判断权限
		if (label.equalsIgnoreCase("speakingcleaner")) {
			if (sender.isOp() == false) {
				sender.sendMessage("权限不足，无法操作！");
				return false;
			}
			// 声明配置文件
			File file = new File(getDataFolder() + "//config.yml");
			FileConfiguration config = YamlConfiguration
					.loadConfiguration(file);
			// 介绍
			if (args.length == 0) {
				sender.sendMessage("==============语言清洁者使用介绍=============");
				sender.sendMessage("=======将违禁词语放到插件文件夹Word.yml中====");
				sender.sendMessage("词语以“,”分隔（注意是半角符号），比如：XX,YY,FF");
				sender.sendMessage("======输入/speakingcleaner s查看更多设置======");
				sender.sendMessage("=======输入/speakingcleaner rl重新加载字库====");
				sender.sendMessage("=========SpeakingCleaner V0.0.1 By:Kuludu====");
			}
			// 设置
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("s")) {
					sender.sendMessage("======================================");
					sender.sendMessage("开启防刷屏/speakingcleaner np e");
					sender.sendMessage("关闭防刷屏/speakingcleaner np d");
					sender.sendMessage("开启语言清洁/speakingcleaner sc e");
					sender.sendMessage("关闭语言清洁/speakingcleaner sc d");
					sender.sendMessage("======================================");
				}
			}

			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("np")) {
					if (args.length >= 2) {
						if (args[1].equalsIgnoreCase("e")) {
							config.set("NoSpam:", "Enable");
							sender.sendMessage("防刷屏开启！");
						}
						if (args[1].equalsIgnoreCase("d")) {
							config.set("NoSpam:", "Disable");
							sender.sendMessage("防刷屏关闭！");
						}
					}
				}

				if (args[0].equalsIgnoreCase("sc")) {
					if (args.length >= 2) {
						if (args[1].equalsIgnoreCase("e")) {
							config.set("SpeakingCleaner:", "Enable");
							sender.sendMessage("语言清洁开启！");
						}
						if (args[1].equalsIgnoreCase("d")) {
							config.set("SpeakingCleaner:", "Disable");
							sender.sendMessage("语言清洁关闭！");
						}

					}
				}

				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 重载
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("rl")) {
					getSetting();
				}
			}
			if (args.length > 4) {
				sender.sendMessage("过多的参数！");
			}
		}
		return false;
	}

	// 插件卸载
	public void onDisable() {
		getLogger().warning("语言清洁者插件已卸载！");
	}

	// 加载插件
	public void onEnable() {
		getLogger().info("语言清洁者插件已加载！");
		getServer().getPluginManager().registerEvents(
				new SpeakingCleanerListener(), this);
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		getSetting();
		try {
			onCheck();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getSetting() {
		// 读取配置文件
		File file = new File(getDataFolder() + "//config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			config.set("NoSpam:", "Enable");
			config.set("SpeakingCleaner:", "Enable");
			config.set("kicktime:", "2");
			try {
				config.save(file);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} else {
			// 获取设置
			String s1 = config.getString("NoSpam:");
			String s2 = config.getString("SpeakingCleaner:");
			kicktime = config.getInt("kicktime");
			if (s1.equalsIgnoreCase("enable")) {
				isNoSpamEnable = true;
			} else {
				isNoSpamEnable = false;
			}
			if (s2.equalsIgnoreCase("enable")) {
				isSpeakingCleanerEnable = true;
			} else {
				isSpeakingCleanerEnable = false;
			}
		}
		// 获取字库
		File Word = new File(getDataFolder() + "//Word.yml");
		if (!Word.exists()) {
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(Word);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				out.write(("Fuck,Shit").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String temp1 = null;
		InputStreamReader read = null;
		try {
			read = new InputStreamReader(new FileInputStream(Word));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(read);
		temp1 = null;
		try {
			temp1 = bufferedReader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 分割字串符
		Main.Word = temp1.split(",");
	}

	public void onCheck() throws IOException {
		final String Currentversion = "V0.1.0";
		final URL updateurl = new URL(
				"http://www.kuludu.net/sc/updateservice/latestversion.html");
		URLConnection conn = updateurl.openConnection();
		java.io.InputStream is = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String Latestversion = br.readLine();
		if (Currentversion.equals(Latestversion)) {
			getLogger().info("插件是最新版本");
		} else {
			getLogger().info(
					"发现最新版本" + Latestversion
							+ "，请前往http://www.kuludu.net/sc/updateservice/"
							+ Latestversion + ".jar下载");
		}
	}
}