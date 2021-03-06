package net.teamcarbon.carbonkit.utils.CarbonVote;

import net.teamcarbon.carbonkit.modules.CarbonVoteModule;
import net.teamcarbon.carbonkit.utils.MiscUtils;
import net.teamcarbon.carbonkit.utils.Module;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Locale;

@SuppressWarnings("UnusedDeclaration")
public class WeatherVote extends Vote {
	/**
	 * Represents the available WeatherTypes for votes
	 */
	public enum WeatherType {
		SUN, STORM, RAIN;
		public String lname() { return name().toLowerCase(); }
	}
	private WeatherType wtype;
	private World world;
	public WeatherVote(OfflinePlayer player, WeatherType wtype, World world) {
		super(player, VoteType.WEATHER);
		this.wtype = wtype;
		this.world = world;
	}
	/**
	 * Fetches the WeatherType associated with this Vote
	 * @return Returns the WeatherType to change to
	 */
	public WeatherType getWeatherType() { return wtype; }
	/**
	 * Fetches the World associated with the Vote
	 * @return Returns the World to change the weather on
	 */
	public World getWorld() { return world; }
	protected void votePass() {
		HashMap<String, String> rep = new HashMap<>();
		rep.put("{WORLD}", world.getName());
		if (wtype.equals(WeatherType.SUN)) {
			world.setStorm(false);
			world.setThundering(false);
			rep.put("{WEATHER}", "sunny");
		} else if (wtype.equals(WeatherType.RAIN)) {
			world.setStorm(true);
			world.setThundering(false);
			rep.put("{WEATHER}", "rainy");
		} else if (wtype.equals(WeatherType.STORM)) {
			world.setStorm(true);
			world.setThundering(true);
			rep.put("{WEATHER}", "stormy");
		}
		MiscUtils.permBroadcast(CarbonVoteModule.VMSG_PERM, Module.getMsg("carbonvote", "weather-vote-passed", true, rep));
	}
	protected void voteFail() {
		HashMap<String, String> rep = new HashMap<>();
		rep.put("{YESPERCENT}", String.format(Locale.ENGLISH, "%.2f", getAgreePercentage(true)));
		rep.put("{NOPERCENT}", String.format(Locale.ENGLISH, "%.2f", (100-getAgreePercentage(true))));
		rep.put("{VOTETYPE}", "Weather");
		MiscUtils.permBroadcast(CarbonVoteModule.VMSG_PERM, Module.getMsg("carbonvote", "vote-failed", true, rep));
	}
	protected void broadcastStart() {
		HashMap<String, String> rep = new HashMap<>();
		rep.put("{VOTETYPE}", "weather");
		rep.put("{VOTEREASON}", "for " + wtype.lname());
		MiscUtils.permBroadcast(CarbonVoteModule.VMSG_PERM, Module.getMsg("carbonvote", "vote-started", true, rep));
	}
	/**
	 * Attempts to resolve a String into a WeatherType
	 * @param type The name of the WeatherType or an alias
	 * @return Returns a WeatherType if a matching alias is found, reverts to WeatherType.SUN otherwise
	 */
	public static WeatherType getWeatherType(String type) {
		if (MiscUtils.eq(type, "storm", "storming", "stormy", "thunder", "thunderstorm", "lightning", "lightningstorm")) {
			return WeatherType.STORM;
		} else if (MiscUtils.eq(type, "rain", "raining", "rainy", "downfall", "downpour")) {
			return WeatherType.RAIN;
		} else {
			return WeatherType.SUN;
		}
	}
}
