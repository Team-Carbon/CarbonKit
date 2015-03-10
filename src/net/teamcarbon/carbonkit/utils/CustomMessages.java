package net.teamcarbon.carbonkit.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import net.teamcarbon.carbonkit.CarbonKit;
import net.teamcarbon.carbonkit.CarbonKit.ConfType;
import net.teamcarbon.carbonlib.Messages.Clr;

@SuppressWarnings("UnusedDeclaration")
public class CustomMessages {
	private final static String CK = "carbonkit.", A = "antiportal.", C = "cmdblocktools.", V = "carbonvote.",
			E = "essentialsassist.", F = "fireworks.", G = "goldensmite", M = "misc.", S = "skullshop.",
			W = "ckwatcher.", GEN = "generic.", T = "carbontrivia.", P = "carbonperks.";
	private static boolean init = false;
	public enum CustomMessage {
		// CORE MODULE
		CORE_PREFIX(CK+"prefix", "&6&l[CarbonKit] &r"),
		CORE_RELOADED(CK+"reloaded", "&bReloaded"),
		CORE_NOT_MODULE(CK+"not-module", "&cCouldn't find a module by that name"),
		CORE_MODULE_ENABLED(CK+"module-enabled", "&aThe {MODULENAME} module has been enabled"),
		CORE_MODULE_DISABLED(CK+"module-disabled", "&cThe {MODULENAME} module has been disabled"),
		CORE_MODULE_ALREADY_ENABLED(CK+"module-already-enabled", "&bThe {MODULENAME} module is already enabled"),
		CORE_MODULE_ALREADY_DISABLED(CK+"module-already-disabled", "&bThis {MODULENAME} module is already disabled"),
		// ANTIPORTAL MODULE
		AP_PREFIX(A+"prefix", "&6&l[AntiPortal] &r"),
		AP_ITEM_RESERVED(A+"item-reserved", "&cThat item is reservered for {PLAYER}. It will be released in {TIME}"),
		// CARBONVOTE MODULE
		CV_PREFIX(V+"prefix", "&6&l[CarbonVote] &a"),
		CV_WEATHER_VOTE_PASSED(V+"weather-vote-passed", "Weather vote passed! Weather changed to &b{WEATHER}"),
		CV_TIME_VOTE_PASSED(V+"time-vote-passed", "Time vote passed! Time changed to &b{TIME}"),
		CV_JAIL_VOTE_PASSED(V+"jail-vote-passed", "Jail vote passed! {TARGET} has been jailed {DURATION}"),
		CV_BAN_VOTE_PASSED(V+"ban-vote-passed", "Ban vote passed! {TARGET} has been banned {DURATION}"),
		CV_KICK_VOTE_PASSED(V+"kick-vote-passed", "Kick vote passed! {TARGET} has been kicked"),
		CV_MUTE_VOTE_PASSED(V+"mute-vote-passed", "Mute vote passed! {TARGET} has been muted {DURATION}"),
		CV_TRIVIA_VOTE_PASSED(V+"trivia-vote-passed", "Trivia vote passed! Starting trivia..."),
		CV_VOTE_FAILED(V+"vote-failed", "{VOTETYPE} vote failed! ({YESPERCENT}% yes, {NOPERCENT}% no)"),
		CV_NO_VOTE(V+"no-vote", "&cThere is no vote going on!"),
		CV_VOTE_EXISTS(V+"vote-exists", "&cThere is already a {VOTETYPE} vote in progress. Use &6/ck y &cor &6/ck n &cto vote."),
		CV_MUST_WAIT(V+"must-wait", "&cA {VOTETYPE} vote has already be called recently. Try again in {REMAININGTIME}!"),
		CV_ALREADY_VOTED(V+"already-voted", "&cYou've already voted!"),
		CV_TRIVIA_DISABLED(V+"trivia-disabled", "&cThe trivia module is disabled"),
		CV_NOT_ENOUGH_PLAYERS(V+"not-enough-players", "&cNot enough players to start a {VOTETYPE} vote, there needs to be {MOREPLAYERS} more"),
		CV_NOT_ENOUGH_MONEY(V+"not-enough-money", "&cYou don't have enough money to start a {VOTETYPE} vote. It costs {VOTECOST}"),
		CV_ECON_ERROR(V+"econ-error", "&cThere was an error withdrawing the money required for this vote"),
		CV_VOTE_STARTED(V+"vote-started", "&b{VOTETYPE} &avote started &b{VOTEREASON}&a, use &6/cv y &aand &6/cv n &ato vote!"),
		CV_OWNER_HELP(V+"starter-help", "&aYour &b{VOTETYPE} &avote is ongoing. ({YESPERCENT}% yes, {NOPERCENT}% no)"),
		CV_VOTER_HELP(V+"voter-help", "&aA &b{VOTETYPE} &avote &b{VOTEREASON} &ais ongoing. Use &6/cv y &aor &6/cv n &ato vote."),
		CV_INVALID_VOTETYPE(V+"invalid-votetype", "&cInvalid vote-type specified. Use &6/cv &cfor more help"),
		CV_VOTE_CAST(V+"vote-cast", "&a{VOTETYPE} vote cast"),
		CV_VOTE_CAST_BROADCAST(V+"vote-cast-broadcast", "&b{VOTER} &ahas vote &b{VOTED} &athe pending {VOTETYPE} vote"),
		CV_BLOCKED(V+"blocked", "&cVoting is temporarily disabled"),
		CV_EXEMPT(V+"exempt", "&cThat user is exempt from being {ACTION}"),
		CV_MUTE_MESSAGE(V+"mute-message", "&cYou've been muted {DURATION}"),
		CV_JAIL_MESSAGE(V+"jail-message", "&cYou've been jailed {DURATION}"),
		CV_BAN_MESSAGE(V+"ban-message", "&cYou've been banned {DURATION}"),
		CV_KICK_MESSAGE(V+"kick-message", "&cYou've been kicked"),
		CV_JAIL_ERROR(V+"jail-error", "&cThere was an error jailing that user"),
		// CARBONTRIVIA MODULE
		CT_MPREFIX(T+"message-prefix", "&9&l[!] &r&b"),
		CT_QPREFIX(T+"question-prefix", "&b&l[?] &r&a"),
		CT_START(T+"start", "A new trivia event is starting!"),
		CT_PROVIDED_BY(T+"provided-by", "&aThis trivia event brought to you by {STARTER}!"),
		CT_ALREADY_RUNNING(T+"already-running", "&cThere is already a trivia event going!"),
		CT_NO_TRIVIA(T+"no-trivia", "&cThere is no active trivia event!"),
		CT_CHARGED(T+"charged", "&bYou paid {VOTEPRICE} to start a {VOTETYPE} vote"),
		CT_ANSWERED(T+"answered", "{PLAYER} &3has answered right with &b{ANSWER}&3!"),
		CT_SKIPPED(T+"skipped", "&3Time is up! Moving on to the next question!"),
		CT_NO_WINS(T+"no-wins", "&3Trivia event ended with no winners!"),
		CT_PLAYER_WINS(T+"player-wins", "&b{PLAYER} &3has won the trivia event with &b{POINTS} &3points!"),
		CT_MULTI_WINS(T+"multi-wins", "&6The winners of the trivia are (tied with &e{POINTS} &6points): &b{PLAYERS}"),
		CT_GENERIC_END(T+"generic-end", "&bTrivia event has ended!"),
		CT_REWARDED(T+"rewarded", "&bHere's your rewards for winning the trivia event!"),
		CT_EXCESS_REWARDS(T+"excess-rewards", "&6Some rewards didn't fit! They'll be dropped here."),
		CT_CHEAT_DETECT(T+"cheat-detected", "&c{PLAYER} was disqualified for answering too quickly!"),
		// CARBONPERKS MODULE
		CP_PREFIX(P+"prefix", "&6&l[Perks] &r"),
		CP_TOGGLED(P+"toggled", "&aParticle trail has been &b{TRAILSTATE}"),
		CP_SET_TRAIL(P+"set-trail", "&aSet &b{EFFECTCOUNT} &atrail effect(s): &7{EFFECTLIST}"),
		CP_SET_RANDOM(P+"set-random", "&aSet &b{EFFECTCOUNT} &arandom trail effect(s): &7{EFFECTLIST}"),
		CP_ADD_TRAIL(P+"add-trail", "&aAdded &b{EFFECTCOUNT} &atrail effect(s): &7{EFFECTLIST}"),
		CP_REM_TRAIL(P+"remove-trail", "&aRemoved the &b{EFFECT} &atrail effect"),
		CP_CLEARED(P+"cleared", "&aYour particle trail effect(s) have been removed"),
		CP_MAX_TRAILS(P+"max-trails", "&cYou already have the max number of trails applied!"),
		CP_REM_NOT_FOUND(P+"remove-not-found", "&cYou do not have the specified effect applied"),
		CP_INVALID_TRAIL(P+"invalid-trail", "&4{QUERY} &cwas not a valid effect name"),
		CP_NO_EFFECT_PERM(P+"no-effect-perm", "&cYou don't have permission to use the &4{EFFECT} &ceffect"),
		// COMMANDBLOCKTOOLS MODULE
		CB_PREFIX(C+"prefix", "&6&l[CmdBlockTools] &r"),
		CB_NOT_CMD_BLOCK(C+"not-command-block", "&cThis command must be executed from a command block!"),
		// ESSASSIST MODULE
		EA_PREFIX(E+"prefix", "&6&l[EssAssist] &r"),
		EA_ANTIINTERACT_ENALBED_SELF(E+"anti-interact-enabled-self", "&bEnabled anti-interact"),
		EA_ANTIINTERACT_DISALBED_SELF(E+"anti-interact-disabled-self", "&bDisabled anti-interact"),
		EA_ANTIINTERACT_ENALBED_OTHER(E+"anti-interact-enabled-others", "&b{PLAYER}'s anti-interact has been enabled"),
		EA_ANTIINTERACT_DISALBED_OTHER(E+"anti-interact-disabled-others", "&b{PLAYER}'s anti-interact has been disabled"),
		EA_ANTIINTERACT_AUTO_DISABLE(E+"anti-interact-auto-disable", "&bYou're not vanished, anti-interact disabled!"),
		EA_ANTIINTERACT_AUTO_ENABLE(E+"anti-interact-auto-disable", "&bYou're vanished, anti-interact enabled!"),
		// FIREWORKS MODULE
		FW_PREFIX(F+"prefix", "&6&l[Fireworks] &r"),
		FW_PRESET_NOT_FOUND(F+"preset-not-found", "&cThat firework preset couldn't be found!"),
		FW_DELETED_PRESET(F+"deleted-preset", "&bThe firework preset was deleted successfully"),
		FW_ERROR_DELETING_PRESET(F+"error-deleting-preset", "&cAn error occurred while trying to delete the firework preset"),
		FW_INVALID_EFFECTS(F+"invlid-effects", "&cCould not parse the arguments into firework effects"),
		FW_COMBINED_EFFECTS(F+"combined-effects", "&aSuccessfully added new effects to firework preset"),
		FW_MODIFIED_PRESET(F+"modified-effects", "&aSuccessfully modified firework preset effects"),
		FW_CREATED_PRESET(F+"created-preset", "&aSuccessfully created firework preset"),
		FW_NOT_FIREWORK(F+"not-firework", "&cYou must be holding a firework in hand to do that"),
		FW_ARROW_ON_SELF(F+"enabled-arrow", "&bFirework arrows are now enabled"),
		FW_ARROW_OFF_SELF(F+"disabled-arrow", "&bFirework arrows are now disabled"),
		FW_ARROW_ON_OTHER(F+"enabled-arrow-others", "&bFirework arrows have been anbled for {PLAYER}"),
		FW_ARROW_OFF_OTHER(F+"disabled-arrow-others", "&bFirework arrows have been disabled for {PLAYER}"),
		FW_SNOWBALL_ON_SELF(F+"enabled-snowball", "&bFirework snowballs are now enabled"),
		FW_SNOWBALL_OFF_SELF(F+"disabled-snowball", "&bFirework snowballs are now disabled"),
		FW_SNOWBALL_ON_OTHER(F+"enabled-snowball-others", "&bFirework snowballs have been anbled for {PLAYER}"),
		FW_SNOWBALL_OFF_OTHER(F+"disabled-snowball-others", "&bFirework snowballs have been disabled for {PLAYER}"),
		FW_EFFECT_SET_SELF(F+"effect-set-others", "&bFirework effect has been set"),
		FW_EFFECT_SET_OTHER(F+"effect-set-others", "&bFirework effect has been set for {PLAYER}"),
		FW_INVENTORY_FULL_SELF(F+"inventory-full", "&cYou're inventory is full! You need one empty slot for that"),
		FW_INVENTORY_FULL_OTHER(F+"inventory-full-others", "&c{PLAYER}'s inventory is full! They need one empty slot for that"),
		// GOLDENSMITE MODULE
		GS_PREFIX(G+"prefix", "&6&l[GSmite] &r"),
		GS_TOGGLED_GROUP(G+"toggled-group", "&bYou've {STATUS} the {GROUP} group"),
		GS_TOGGLED_ARROW(G+"toggled-arrow", "&bYou've {STATUS} GSmite arrows"),
		GS_TOGGLED_SNOWBALL(G+"toggled-snowball", "&bYou've {STATUS} GSmite snowballs"),
		// MISC MODULE
		MISC_PREFIX(M+"prefix", "&6&l[CarbonKit] &r"),
		MISC_FREEZE(M+"freeze-message", "&bFreeze!"),
		MISC_UNFREEZE(M+"unfreeze-message", "&6You're free to go!"),
		MISC_SLAPPED(M+"slapped", "&6You've been slapped!"),
		MISC_SLAPPED_BROADCAST(M+"slapped-broadcast", "&6{SLAPPER} has slapped {SLAPPED}!"),
		MISC_JOIN(M+"join-message", "&6&l[&r&a+&r&6&l] &e{PLAYER}&r&6 logged in!"),
		MISC_QUIT(M+"quit-message", "&6&l[&r&c-&r&6&l] &e{PLAYER}&r&6 logged out!"),
		MISC_JOIN_EXT(M+"join-message-extedned", "&7{STATUS}&6&l[&r&a+&r&6&l] &e{PLAYER} &r&7&o({IP})"),
		MISC_QUIT_EXT(M+"quit-message-extended", "&7{STATUS}&6&l[&r&c-&r&6&l] &e{PLAYER}"),
		MISC_INVALID_MODE(M+"invalid-gamemode", "&cThat does not appear to be a valid gamemode!"),
		MISC_MODE_SET_SELF(M+"gamemode-set-self", "&aYour gamemode has been set to &b{MODE}"),
		MISC_MODE_SET_OTHER(M+"gammoede-set-other", "You've set &b{USER}'s &agamemode to &b{MODE}"),
		MISC_MODE_CHANGE(M+"gamemode-change", "&aYour gamemode has been set to &b{MODE}"),
		// SKULLSHOP MODULE
		SS_PREFIX(S+"prefix", "&6&l[SkullShop] &r"),
		SS_INVENTORY_FULL(S+"inventory-full", "&cYour inventory is full! Try again when you have at least one slot open"),
		SS_SKULL_GIVEN(S+"skull-given", "&bYou've been given &a{SKULLOWNER}'s &bhead for ${PRICE}!"),
		SS_UPDATED_GIVEN(S+"skull-updated", "&bYou've been given &a{SKULLOWNER}'s &bhead for ${PRICE}!"),
		SS_SKULL_GIVEN_FREE(S+"skull-given-free", "&bYou've been given &a{SKULLOWNER}'s &bhead"),
		SS_UPDATED_GIVEN_FREE(S+"skull-updated-free", "&bYou've been given &a{SKULLOWNER}'s &bhead"),
		SS_SKULL_CHECK(S+"skull-check", "&bThis is &a{SKULLOWNER}'s &bhead."),
		SS_GET_SKULL(S+"get-skull", "&bSaved &a{SKULLOWNER}'s &bhead. Type &6/getskull &bto buy it for ${PRICE}!"),
		SS_GET_SKULL_FREE(S+"get-skull-free", "&bSaved &a{SKULLOWNER}'s &bhead. Type &6/getskull &bto get it!"),
		SS_NO_SKULL_SAVED(S+"no-skull-saved", "&cThere is no skull saved. Right click one to save it."),
		SS_NOT_ENOUGH_MONEY(S+"not-enough-money", "&cYou can't afford to buy a player head! They cost {SKULLCOST}"),
		SS_TRANSACTION_FAILED(S+"transaction-failed", "&cThere was an error withdrawing the money required for this head"),
		// CKWATCHER MESSAGES
		CW_PREFIX(W+"prefix", "&6&l[CKWatcher] &r"),
		CW_WATCH_ENABLED(W+"watch-enabled", "&bYou are now watching commands"),
		CW_WATCH_DISABLED(W+"watch-disabled", "&5You are no longer watching commands"),
		CW_WATCH_ENABLED_OTHER(W+"watch-enabled-others", "&bCommand watching for {PLAYER} enabled"),
		CW_WATCH_DISABLED_OTHER(W+"watch-disabled-others", "&5Command watching for {PLAYER} disabled"),
		// GENERIC MESSAGES
		GEN_PREFIX(GEN +"prefix", "&6&l[CarbonKit] &r"),
		GEN_NO_PERM(GEN +"no-perm", "&cYou don't have permission to do that"),
		GEN_NOT_ONLINE(GEN +"not-online", "&cYou must be in-game to use that"),
		GEN_NEEDS_GROUP(GEN +"needs-group", "&cYou must specify a group!"),
		GEN_GENERIC_ERROR(GEN +"generic-error", "&cAn error occurred. Please report this to an admin!"),
		GEN_PLAYER_NOT_FOUND(GEN +"player-not-found", "&cThat player couldn't be found");
		private String msg, path, defMsg;
		CustomMessage(String path, String defMsg) { this.path = path; this.defMsg = defMsg; }
		public String getPath() { return path; }
		public String noPre() { if (!init) loadMessages(); return Clr.trans(msg); }
		public String pre() {
			if (!init)
				loadMessages();
			for (CustomMessage m : CustomMessage.values())
				if (m.name().equalsIgnoreCase(name().substring(0, name().indexOf("_")) + "_PREFIX"))
					return Clr.trans(m + "" + msg);
			return Clr.trans(msg);
		}
		/**
		 * Sets the cached message for this enumarated object
		 * @param message The message to set this CustomMessage to
		 */
		public void setMessage(String message) { msg = Clr.trans(message); }
		/**
		 * Attempts to load all CustomMessage objects from disk, filling in missing messages with default values
		 */
		public static void loadMessages() {
			FileConfiguration msgs = CarbonKit.getConfig(ConfType.MESSAGES);
			for (CustomMessage cm : CustomMessage.values()) {
				cm.msg = msgs.getString(cm.path, cm.defMsg);
				msgs.set(cm.path, cm.msg);
			}
			CarbonKit.saveConfig(ConfType.MESSAGES);
			init = true;
		}
		/**
		 * Prints Fireworks parameters to the specified CommandSender
		 * @param sender The CommandSender to send the message to
		 */
		public static void remindFireworkParams(CommandSender sender) {
			printHeader(sender, "Valid Firework Preset Parameters");
			sender.sendMessage(Clr.AQUA + "-tf" + Clr.DARKAQUA + " Trail and flicker i.e. " + Clr.GRAY + "-t, -f, or -tf");
			sender.sendMessage(Clr.AQUA + "c:RRGGBB,color" + Clr.DARKAQUA + " Primary colors, i.e. " + Clr.GRAY + "c:FF0000,blue");
			sender.sendMessage(Clr.AQUA + "f:RRGGBB,color" + Clr.DARKAQUA + " Fade colors (same format as primary)");
			sender.sendMessage(Clr.AQUA + "s:shape" + Clr.DARKAQUA + " Shape. i.e. " + Clr.GRAY + "ball, large, burst, star, creeper");
			sender.sendMessage(Clr.GOLD + "For several effects, wrap params in {}, i.e. " + Clr.GRAY + "{params}{params}");
		}
		/**
		 * Prints a formatted header for the specified CommandSender
		 * @param sender The CommandSender to send the message to
		 * @param header The header to print (is wrapped in brackets)
		 */
		public static void printHeader(CommandSender sender, String header) {
			if (header != null && !header.isEmpty())
				sender.sendMessage(Clr.fromChars("6l") + "===[ " + header + " ]======");
			else printFooter(sender);
		}
		/**
		 * Prints a formatted footer for the specified CommandSender
		 * @param sender The CommandSender to send the message to
		 */
		public static void printFooter(CommandSender sender) { sender.sendMessage(Clr.fromChars("6l") + "============="); }
		public String toString() {
			if (!init)
				loadMessages();
			return msg;
		}
	}
}
