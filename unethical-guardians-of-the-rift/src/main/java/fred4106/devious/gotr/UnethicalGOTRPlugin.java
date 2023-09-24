package fred4106.devious.gotr;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.EntityNameable;
import net.unethicalite.api.Interactable;
import net.unethicalite.api.items.Inventory;
import org.pf4j.Extension;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@PluginDescriptor(
		name = "Unethical Guardians of the Rift",
		description = "Adds some automatic reactions while playing Guardians of the Rift.",
		enabledByDefault = false
)
@Slf4j
@Extension
public class UnethicalGOTRPlugin extends Plugin {
	@Inject
	private UnethicalGOTRConfig config;

	@Inject
	private Client client;

	@Inject
	private KeyManager keyManager;

	private static final String ONECLICK_MENUOPTION_PREFIX = "<col=00ff00>OC:</col> ";

	private static final List<Integer> GAME_OBJECT_OPCODES = List.of(1, 2, 3, 4, 5, 6, 1001, 1002);
	private static final List<Integer> NPC_OPCODES = List.of(7, 8, 9, 10, 11, 12, 13, 1003);
	private static final List<Integer> GROUND_ITEM_OPCODES = List.of(18, 19, 20, 21, 22, 1004);
	private static final List<Integer> ITEM_OPCODES = List.of(1007, 25, 57);
	private static final List<Integer> PLAYER_OPCODES = List.of(44, 45, 46, 47, 48, 49, 50, 51);

//	private final Map<String, String> gameObjectConfigs = new HashMap<>();
//	private final Map<String, String> npcConfigs = new HashMap<>();
//	private final Map<String, String> groundItemConfigs = new HashMap<>();
//	private final Map<String, String> itemConfigs = new HashMap<>();
//	private final Map<String, String> playerConfigs = new HashMap<>();

	private KeyListener allKeyListener = new KeyListener() {
		private void logEvent(KeyEvent e) {
			String eType = "Unknown";
			if (e.getID() == KeyEvent.KEY_TYPED) {
				eType = "Typed";
			} else if (e.getID() == KeyEvent.KEY_PRESSED) {
				eType = "Pressed";
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				eType = "Released";
			}
			List<String> modifiers = new ArrayList<String>();
			if (e.isShiftDown()) {
				modifiers.add("shift");
			}
			if (e.isControlDown()) {
				modifiers.add("ctrl");
			}
			if (e.isAltDown()) {
				modifiers.add("alt");
			}
			if (e.isMetaDown()) {
				modifiers.add("meta");
			}
			if (e.isAltGraphDown()) {
				modifiers.add("altGr");
			}
			StringBuilder modifiersStr = new StringBuilder();
			for (int i = 0; i < modifiers.size(); i++) {
				modifiersStr.append(modifiers.get(i));
				if (i > 0 && i < modifiers.size() - 1) {
					modifiersStr.append(" ");
				}
			}
			String location = List.of("KEY_LOCATION_UNKNOWN", "KEY_LOCATION_STANDARD", "KEY_LOCATION_LEFT", "KEY_LOCATION_RIGHT", "KEY_LOCATION_NUMPAD").get(e.getKeyLocation());
			log.info("Key {}: code={} extended={} char='{}' modifiers='{}' location={}", eType, e.getKeyCode(), e.getExtendedKeyCode(), e.getKeyChar(), modifiersStr.toString(), location);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			logEvent( e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			logEvent(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			logEvent(e);
		}
	};

	@Provides
	public UnethicalGOTRConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(UnethicalGOTRConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e) {
		if (!e.getGroup().equals("hootoneclick")) {
			return;
		}

		clearConfigs();

//		parseConfigs(config.gameObjectConfig(), gameObjectConfigs);
//		parseConfigs(config.npcConfig(), npcConfigs);
//		parseConfigs(config.groundItemConfig(), groundItemConfigs);
//		parseConfigs(config.itemConfig(), itemConfigs);
//		parseConfigs(config.playerConfig(), playerConfigs);
	}


	@Override
	protected void startUp() throws Exception {
		keyManager.registerKeyListener(allKeyListener);
	}

	@Override
	protected void shutDown() throws Exception {
		keyManager.unregisterKeyListener(allKeyListener);
	}


	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded e) {
		if (e.getOption().startsWith(ONECLICK_MENUOPTION_PREFIX)) {
			return;
		}

		int opcode = e.getType();

//		if (!gameObjectConfigs.isEmpty() && GAME_OBJECT_OPCODES.contains(opcode))
//		{
//			Tile tile = client.getScene().getTiles()[client.getPlane()][e.getActionParam0()][e.getActionParam1()];
//			TileObject obj = TileObjects.getFirstAt(tile, e.getIdentifier());
//			MenuEntry replaced = replace(gameObjectConfigs, obj);
//			if (replaced != null)
//			{
//				client.setMenuEntries(new MenuEntry[]{replaced});
//				return;
//			}
//		}
//
//		if (!npcConfigs.isEmpty() && NPC_OPCODES.contains(opcode))
//		{
//			NPC npc = NPCs.getNearest(x -> x.getIndex() == e.getIdentifier());
//			MenuEntry replaced = replace(npcConfigs, npc);
//			if (replaced != null)
//			{
//				client.setMenuEntries(new MenuEntry[]{replaced});
//				return;
//			}
//		}
//
//		if (!groundItemConfigs.isEmpty() && GROUND_ITEM_OPCODES.contains(opcode))
//		{
//			Tile tile = client.getScene().getTiles()[client.getPlane()][e.getActionParam0()][e.getActionParam1()];
//			TileItem item = TileItems.getFirstAt(tile, e.getIdentifier());
//			MenuEntry replaced = replace(groundItemConfigs, item);
//			if (replaced != null)
//			{
//				client.setMenuEntries(new MenuEntry[]{replaced});
//				return;
//			}
//		}
//
//		if (!itemConfigs.isEmpty() && ITEM_OPCODES.contains(opcode))
//		{
//			Item item = Inventory.getItem(e.getActionParam0());
//			MenuEntry replaced = replace(itemConfigs, item);
//			if (replaced != null)
//			{
//				client.setMenuEntries(new MenuEntry[]{replaced});
//				return;
//			}
//		}
//
//		if (!playerConfigs.isEmpty() && PLAYER_OPCODES.contains(opcode))
//		{
//			Player player = Players.getNearest(x -> x.getIndex() == e.getIdentifier());
//			MenuEntry replaced = replace(playerConfigs, player);
//			if (replaced != null)
//			{
//				client.setMenuEntries(new MenuEntry[]{replaced});
//			}
//		}
	}

	private <T extends Interactable> MenuEntry replace(Map<String, String> replacements, T target) {
		if (!(target instanceof EntityNameable)) {
			return null;
		}

		if (!replacements.containsKey(((EntityNameable) target).getName())
				&& replacements.keySet().stream().noneMatch(x -> ((EntityNameable) target).getName().toLowerCase().contains(x.toLowerCase())))
		{
			return null;
		}

		String replacement = replacements.get(((EntityNameable) target).getName());

		if (replacement == null) {
			log.debug("Replacement was null for {}", target);
			return null;
		}

		if (isUseOn(replacement)) {
			String itemName = replacement.substring(4);
			if (isId(itemName)) {
				Item usedItem = Inventory.getFirst(Integer.parseInt(itemName));
				if (usedItem != null) {
					return useOn(usedItem, target).setForceLeftClick(true);
				}
			} else {
				Item usedItem = getUsedItem(replacement);
				if (usedItem != null) {
					return useOn(usedItem, target).setForceLeftClick(true);
				}
			}

			log.debug("Used item was null for replacement: {}", replacement);
			return null;
		}

		if (!target.hasAction(replacement)) {
			return null;
		}

		return target.getMenu(replacement).toEntry(client, 0)
				.setOption(ONECLICK_MENUOPTION_PREFIX + replacement)
				.setTarget(((EntityNameable) target).getName())
				.setForceLeftClick(true);
	}

	private MenuEntry useOn(Item item, Interactable target) {
		if (target instanceof TileItem) {
			return target.getMenu(0, MenuAction.WIDGET_TARGET_ON_GROUND_ITEM.getId()).toEntry(client, 0)
					.setOption(ONECLICK_MENUOPTION_PREFIX + item.getName() + " ->")
					.setTarget(((TileItem) target).getName())
					.onClick(x -> item.use());
		}

		if (target instanceof TileObject) {
			return target.getMenu(0, MenuAction.WIDGET_TARGET_ON_GAME_OBJECT.getId()).toEntry(client, 0)
					.setOption(ONECLICK_MENUOPTION_PREFIX + item.getName() + " ->")
					.setTarget(((TileObject) target).getName())
					.onClick(x -> item.use());
		}

		if (target instanceof Item) {
			return target.getMenu(0, MenuAction.WIDGET_TARGET_ON_WIDGET.getId()).toEntry(client, 0)
					.setOption(ONECLICK_MENUOPTION_PREFIX + item.getName() + " ->")
					.setTarget(((Item) target).getName())
					.onClick(x -> item.use());
		}

		if (target instanceof Actor) {
			MenuAction menuAction = target instanceof NPC ? MenuAction.WIDGET_TARGET_ON_NPC : MenuAction.WIDGET_TARGET_ON_PLAYER;
			return target.getMenu(0, menuAction.getId()).toEntry(client, 0)
					.setOption(ONECLICK_MENUOPTION_PREFIX + item.getName() + " ->")
					.setTarget(((Actor) target).getName())
					.onClick(x -> item.use());
		}

		return null;
	}

	private Item getUsedItem(String action) {
		return Inventory.getFirst(x -> x.getName().equals(action.substring(4)));
	}

	private boolean isUseOn(String action) {
		return action.contains("Use ") && action.split(" ").length >= 2;
	}

	private void clearConfigs() {

	}

	private boolean isId(String text) {
		if (text == null) {
			return false;
		}
		try {
			Integer.parseInt(text);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}
}
