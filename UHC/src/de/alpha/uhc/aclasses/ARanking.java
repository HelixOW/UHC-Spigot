package de.alpha.uhc.aclasses;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.mysql.MySQLAPI;

public class ARanking {

	private Core pl;
	private Registery r;
	private HashMap<Integer, String> rank = new HashMap<>();

	private Location firstPlace;
	private Location secondPlace;
	private Location thirdPlace;

	private String line1;
	private String line2;
	private String line3;
	private String line4;

	public ARanking(Core plug) {
		pl = plug;
		r = pl.getRegistery();
	}

	public void update() {

		if (firstPlace == null || secondPlace == null || thirdPlace == null) {
			System.out.println("null");
			return;
		}

		try {
			ResultSet rs = MySQLAPI.getMySQLConnection().createStatement()
					.executeQuery("SELECT " + "UUID" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");
			int in = MySQLAPI.getCountNumber() + 1;

			while (rs.next()) {
				in--;
				rank.put(in, rs.getString("UUID"));
			}

			LinkedList<Location> locs = new LinkedList<>();

			locs.add(firstPlace.add(0, 1, 0));
			locs.add(secondPlace.add(0, 1, 0));
			locs.add(thirdPlace.add(0, 1, 0));

			for (int i = 0; i < locs.size(); i++) {
				int id = i + 1;

				if (!(locs.get(i).getBlock().getType().equals(Material.SKULL)))
					locs.get(i).getBlock().setType(Material.SKULL);
				Skull s = (Skull) locs.get(i).getBlock().getState();

				s.setSkullType(SkullType.PLAYER);

				if (!(rank.containsKey(id)))
					continue;
				String name = Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))).getName();

				s.setOwner(name);
				s.update();

				Location sig = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() - 1,
						locs.get(i).getZ());

				if (sig.getBlock().getState() instanceof Sign) {
					Sign sign = (Sign) sig.getBlock().getState();

					String a = line1.replace("[rank]", Integer.toString(id)).replace("[player]", name)
							.replace("[points]", Integer.toString(
									r.getStats().getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
					String b = line2.replace("[rank]", Integer.toString(id)).replace("[player]", name)
							.replace("[points]", Integer.toString(
									r.getStats().getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
					String c = line3.replace("[rank]", Integer.toString(id)).replace("[player]", name)
							.replace("[points]", Integer.toString(
									r.getStats().getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
					String d = line4.replace("[rank]", Integer.toString(id)).replace("[player]", name)
							.replace("[points]", Integer.toString(
									r.getStats().getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));

					sign.setLine(0, a);
					sign.setLine(1, b);
					sign.setLine(2, c);
					sign.setLine(3, d);

					sign.update();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCMD() {

		if (firstPlace == null || secondPlace == null || thirdPlace == null) {
			System.out.println("null");
			return;
		}

		try {
			ResultSet rs = MySQLAPI.getMySQLConnection().createStatement()
					.executeQuery("SELECT " + "UUID" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");
			int in = MySQLAPI.getCountNumber() + 1;

			while (rs.next()) {
				in--;
				rank.put(in, rs.getString("UUID"));
			}

			LinkedList<Location> locs = new LinkedList<>();

			locs.add(firstPlace);
			locs.add(secondPlace);
			locs.add(thirdPlace);

			for (int i = 0; i < locs.size(); i++) {
				int id = i + 1;

				if (!(locs.get(i).getBlock().getType().equals(Material.SKULL)))
					locs.get(i).getBlock().setType(Material.SKULL);
				Skull s = (Skull) locs.get(i).getBlock().getState();

				s.setSkullType(SkullType.PLAYER);

				if (!(rank.containsKey(id)))
					continue;
				String name = Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))).getName();

				s.setOwner(name);
				s.update();

				Location sig = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() - 1,
						locs.get(i).getZ());

				if (sig.getBlock().getState() instanceof Sign) {
					Sign sign = (Sign) sig.getBlock().getState();

					String a = line1.replace("[rank]", Integer.toString(id)).replace("[player]", name)
							.replace("[points]", Integer.toString(
									r.getStats().getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
					String b = line2.replace("[rank]", Integer.toString(id)).replace("[player]", name)
							.replace("[points]", Integer.toString(
									r.getStats().getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
					String c = line3.replace("[rank]", Integer.toString(id)).replace("[player]", name)
							.replace("[points]", Integer.toString(
									r.getStats().getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
					String d = line4.replace("[rank]", Integer.toString(id)).replace("[player]", name)
							.replace("[points]", Integer.toString(
									r.getStats().getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));

					sign.setLine(0, a);
					sign.setLine(1, b);
					sign.setLine(2, c);
					sign.setLine(3, d);

					sign.update();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFirstPlace(Location firstPlace) {
		this.firstPlace = firstPlace;
	}

	public void setSecondPlace(Location secondPlace) {
		this.secondPlace = secondPlace;
	}

	public void setThirdPlace(Location thirdPlace) {
		this.thirdPlace = thirdPlace;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public String getLine4() {
		return line4;
	}

	public void setLine4(String line4) {
		this.line4 = line4;
	}

}
