package essentials.future.code.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLPlayer {

	public static boolean userExists(UUID uuid) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Name FROM Players WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getUUIDFromName(String name) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT UUID FROM Players WHERE Name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString("UUID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean userExistsByName(String name) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT UUID FROM Players WHERE Name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void isConnected() {
		if (!MySQL.isConnected()) {
			
		}
	}

	public static void updateCoins(UUID uuid, int amount, boolean remove, String playername) {
		isConnected();
		int coins = getCoins(uuid.toString());

		if (remove) {
			coins -= amount;
		} else {
			coins = amount;
		}

		if (userExists(uuid)) {
			try {
				PreparedStatement ps = MySQL.getConnection()
						.prepareStatement("UPDATE Players SET Coins = ? WHERE UUID = ?");
				ps.setInt(1, coins);
				ps.setString(2, uuid.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void setBanned(String uuid, Boolean state) {
		
		int i = 0;
		
		if(state == false) {
			i = 0;
		} else {
			i = 1;
		}
		
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE Players SET Baned = ? WHERE UUID = ?");
			ps.setInt(1, i);
			ps.setString(2, uuid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void setBanTime(String uuid, long time) {
		
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE Players SET BanTime = ? WHERE UUID = ?");
			ps.setLong(1, time);
			ps.setString(2, uuid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setBanReason(String uuid, String reason) {
		
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE Players SET BanReason = ? WHERE UUID = ?");
			ps.setString(1, reason);
			ps.setString(2, uuid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setMuted(String uuid, Boolean state) {
		
		int i = 0;
		
		if(state == false) {
			i = 0;
		} else {
			i = 1;
		}
		
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE Players SET Muted = ? WHERE UUID = ?");
			ps.setInt(1, i);
			ps.setString(2, uuid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setMuteTime(String uuid, long time) {
		
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE Players SET MuteTime = ? WHERE UUID = ?");
			ps.setLong(1, time);
			ps.setString(2, uuid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setMuteReason(String uuid, String reason) {
		
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE Players SET MuteReason = ? WHERE UUID = ?");
			ps.setString(1, reason);
			ps.setString(2, uuid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createPlayerRow(UUID uuid, String playername, String group) {
		if (!userExists(uuid)) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement(
						"INSERT INTO Players (UUID,Name,Baned,BanTime,BanReason,Muted,MuteTime,MuteReason,Coins,Rang) VALUES (?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, uuid.toString());
				ps.setString(2, playername);
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.setString(5, "null");
				ps.setInt(6, 0);
				ps.setInt(7, 0);
				ps.setString(8, "null");
				ps.setInt(9, 100);
				ps.setString(10, group);
				ps.executeUpdate();
				System.out.println("Essentials: Player Row createt for player: " + playername);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void delete(UUID uuid) {
		isConnected();
		if (userExists(uuid)) {
			try {
				PreparedStatement ps = MySQL.getConnection()
						.prepareStatement("DELETE * FROM CoinSystem WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("[MySQL] Der Spieler mit der UUID: " + uuid.toString() + " ist nicht eingetragen");
		}
	}

	public static Integer getCoins(String uuid) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Coins FROM Players WHERE UUID = ?");
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("Coins");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	public static String getGroupFromPlayer(String uuid) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Rang FROM Players WHERE UUID = ?");
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString("Rang");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "null";
	}

	public static boolean isBaned(String uuid) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Baned FROM Players WHERE UUID = ?");
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int i = rs.getInt("Baned");
				if (i == 0) {
					return false;
				} else {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static long getBanTime(UUID uuid) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT BanTime FROM Players WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getLong("BanTime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public static Boolean isMuted(String UUID) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Muted FROM Players WHERE UUID = ?");
			ps.setString(1, UUID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int i = rs.getInt("Muted");
				if (i == 0) {
					return false;
				} else {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public static long getMuteTime(UUID uuid) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("SELECT MuteTime FROM Players WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getLong("MuteTime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	public static String getMuteReason(UUID uuid) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("SELECT MuteReason FROM Players WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString("MuteReason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String getBanReason(UUID uuid) {
		isConnected();
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("SELECT BanReason FROM Players WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString("BanReason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static void addKills(UUID uuid) {
		int kills = getKills(uuid);
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE Players SET Kills = ? WHERE UUID = ?");
			ps.setInt(1, kills+1);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int getKills(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("SELECT Kills FROM Players WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("Kills");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void addDeaths(UUID uuid) {
		int deaths = getDeaths(uuid);
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE Players SET Deaths = ? WHERE UUID = ?");
			ps.setInt(1, deaths+1);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int getDeaths(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("SELECT Deaths FROM Players WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("Deaths");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}