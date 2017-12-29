package services.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import l2f.commons.dbutils.DbUtils;
import l2f.gameserver.Config;
import l2f.gameserver.data.htm.HtmCache;
import l2f.gameserver.database.DatabaseFactory;
import l2f.gameserver.handler.bbs.CommunityBoardManager;
import l2f.gameserver.handler.bbs.ICommunityBoardHandler;
import l2f.gameserver.model.Player;
import l2f.gameserver.model.base.Experience;
import l2f.gameserver.network.serverpackets.ExBR_GamePoint;
import l2f.gameserver.network.serverpackets.ShowBoard;
import l2f.gameserver.scripts.Functions;
import l2f.gameserver.scripts.ScriptFile;
import l2f.gameserver.tables.ClanTable;

/**
 * @author RobikBobik
 */
public class CommunityBoard extends Functions implements ScriptFile, ICommunityBoardHandler
{
	@Override
	public String[] getBypassCommands()
	{
		return new String[]
		{
			"_bbshome",
			"_bbsstat_pk",
			"_bbsstat_pvp",
			"_bbsstat_online",
			"_bbsstat_clan",
			"_bbsstat_castle",
			"_bbsstat_top_pc_bangs",
			"_bbsstat_top_fame",
			"_bbsservice_delevel",
			"_bbsservice_col_to_psp",
		};
	}
	
	public class StatisticData
	{
		public int PlayerId = 0;
		public String ChName = "";
		public int ChGameTime = 0;
		public int ChPk = 0;
		public int ChPvP = 0;
		public int ChOnOff = 0;
		public int ChSex = 0;
		public int PcBangs = 0;
		public int Fame = 0;
		public String NameCastl;
		public Object siegeDate;
		public String Percent;
		public Object id2;
		public int id;
		public int ClanLevel;
		public int hasCastle;
		public int ReputationClan;
		public String AllyName;
		public String ClanName;
		public String Owner;
	}
	
	@Override
	public void onBypassCommand(Player player, String command)
	{
		// unstable code
		StringTokenizer st = new StringTokenizer(command, "_");
		String cmd = st.nextToken();
		String html = "";
		if ("bbshome".equals(cmd))
		{
			StringTokenizer p = new StringTokenizer(Config.BBS_DEFAULT, "_");
			String dafault = p.nextToken();
			if (dafault.equals(cmd))
			{
				html = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/index.htm", player);
				
				int favCount = 0;
				Connection con = null;
				PreparedStatement statement = null;
				ResultSet rset = null;
				try
				{
					con = DatabaseFactory.getInstance().getConnection();
					statement = con.prepareStatement("SELECT count(*) as cnt FROM `bbs_favorites` WHERE `object_id` = ?");
					statement.setInt(1, player.getObjectId());
					rset = statement.executeQuery();
					if (rset.next())
					{
						favCount = rset.getInt("cnt");
					}
				}
				catch (Exception e)
				{
				}
				finally
				{
					DbUtils.closeQuietly(con, statement, rset);
				}
				
				html = html.replace("<?fav_count?>", String.valueOf(favCount));
				html = html.replace("<?clan_count?>", String.valueOf(ClanTable.getInstance().getClans().length));
				html = html.replace("<?market_count?>", String.valueOf(CommunityBoardManager.getInstance().getIntProperty("col_count")));
			}
			else
			{
				onBypassCommand(player, Config.BBS_DEFAULT);
				return;
			}
		}
		else if ("bbslink".equals(cmd))
		{
			html = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/index.htm", player);
		}
		else if (command.startsWith("_bbspage"))
		{
			String[] b = command.split(":");
			String page = b[1];
			html = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/" + page + ".htm", player);
		}
		// now stable
		else if (command.startsWith("_bbsstat_pk"))
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM characters WHERE accesslevel = '0' ORDER BY pkkills DESC LIMIT 100;");
				rs = statement.executeQuery();
				
				StringBuilder html2 = new StringBuilder();
				html2.append("<table width=440>");
				while (rs.next())
				{
					StatisticData tp = new StatisticData();
					tp.PlayerId = rs.getInt("obj_Id");
					tp.ChName = rs.getString("char_name");
					tp.ChSex = rs.getInt("sex");
					tp.ChPk = rs.getInt("pkkills");
					tp.ChOnOff = rs.getInt("online");
					
					String sex = tp.ChSex == 1 ? "F" : "M";
					String color;
					String OnOff;
					if (tp.ChOnOff == 1)
					{
						OnOff = "Online.";
						color = "00CC00";
					}
					else
					{
						OnOff = "Offline.";
						color = "D70000";
					}
					html2.append("<tr>");
					html2.append("<td width=150 align=\"center\">" + tp.ChName + "</td>");
					html2.append("<td width=50 align=\"center\">" + sex + "</td>");
					html2.append("<td width=50 align=\"center\"><font color=00CC00>" + tp.ChPk + "</font></td>");
					html2.append("<td width=80 align=\"center\"><font color=" + color + ">" + OnOff + "</font></td>");
					html2.append("</tr>");
				}
				html2.append("</table>");
				String content = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/stats/stats_top_pk.htm", player);
				content = content.replace("%stats_top_pk%", html2.toString());
				ShowBoard.separateAndSend(content, player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rs);
			}
			return;
		}
		else if (command.startsWith("_bbsstat_pvp"))
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM characters WHERE accesslevel = '0' ORDER BY pvpkills DESC LIMIT 100;");
				rs = statement.executeQuery();
				
				StringBuilder html3 = new StringBuilder();
				html3.append("<table width=440>");
				while (rs.next())
				{
					StatisticData tp = new StatisticData();
					tp.PlayerId = rs.getInt("obj_Id");
					tp.ChName = rs.getString("char_name");
					tp.ChSex = rs.getInt("sex");
					tp.ChPvP = rs.getInt("pvpkills");
					tp.ChOnOff = rs.getInt("online");
					
					String sex = tp.ChSex == 1 ? "F" : "М";
					String color;
					String OnOff;
					if (tp.ChOnOff == 1)
					{
						OnOff = "Online.";
						color = "00CC00";
					}
					else
					{
						OnOff = "Offline.";
						color = "D70000";
					}
					html3.append("<tr>");
					html3.append("<td width=150 align=\"center\">" + tp.ChName + "</td>");
					html3.append("<td width=50 align=\"center\">" + sex + "</td>");
					html3.append("<td width=50 align=\"center\"><font color=00CC00>" + tp.ChPvP + "</font></td>");
					html3.append("<td width=80 align=\"center\"><font color=" + color + ">" + OnOff + "</font></td>");
					html3.append("</tr>");
				}
				html3.append("</table>");
				
				String content = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/stats/stats_top_pvp.htm", player);
				
				content = content.replace("%stats_top_pvp%", html3.toString());
				ShowBoard.separateAndSend(content, player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rs);
			}
			return;
		}
		else if (command.startsWith("_bbsstat_top_fame"))
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM characters WHERE accesslevel = '0' ORDER BY fame DESC LIMIT 100;");
				rs = statement.executeQuery();
				
				StringBuilder html4 = new StringBuilder();
				html4.append("<table width=440>");
				while (rs.next())
				{
					StatisticData tp = new StatisticData();
					tp.PlayerId = rs.getInt("obj_Id");
					tp.ChName = rs.getString("char_name");
					tp.ChSex = rs.getInt("sex");
					tp.Fame = rs.getInt("fame");
					tp.ChOnOff = rs.getInt("online");
					
					String sex = tp.ChSex == 1 ? "F" : "М";
					String color;
					String OnOff;
					if (tp.ChOnOff == 1)
					{
						OnOff = "Online.";
						color = "00CC00";
					}
					else
					{
						OnOff = "Offline.";
						color = "D70000";
					}
					html4.append("<tr>");
					html4.append("<td width=150 align=\"center\">" + tp.ChName + "</td>");
					html4.append("<td width=50 align=\"center\">" + sex + "</td>");
					html4.append("<td width=50 align=\"center\"><font color=00CC00>" + tp.Fame + "</font></td>");
					html4.append("<td width=80 align=\"center\"><font color=" + color + ">" + OnOff + "</font></td>");
					html4.append("</tr>");
				}
				html4.append("</table>");
				
				String content = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/stats/stats_top_fame.htm", player);
				
				content = content.replace("%stats_top_fame%", html4.toString());
				ShowBoard.separateAndSend(content, player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rs);
			}
			return;
		}
		else if (command.startsWith("_bbsstat_top_pc_bangs"))
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM characters WHERE accesslevel = '0' ORDER BY pcBangPoints DESC LIMIT 100;");
				rs = statement.executeQuery();
				
				StringBuilder html5 = new StringBuilder();
				html5.append("<table width=440>");
				while (rs.next())
				{
					StatisticData tp = new StatisticData();
					tp.PlayerId = rs.getInt("obj_Id");
					tp.ChName = rs.getString("char_name");
					tp.ChSex = rs.getInt("sex");
					tp.PcBangs = rs.getInt("pcBangPoints");
					tp.ChOnOff = rs.getInt("online");
					
					String sex = tp.ChSex == 1 ? "F" : "М";
					String color;
					String OnOff;
					if (tp.ChOnOff == 1)
					{
						OnOff = "Online.";
						color = "00CC00";
					}
					else
					{
						OnOff = "Offline.";
						color = "D70000";
					}
					html5.append("<tr>");
					html5.append("<td width=150 align=\"center\">" + tp.ChName + "</td>");
					html5.append("<td width=50 align=\"center\">" + sex + "</td>");
					html5.append("<td width=50 align=\"center\"><font color=00CC00>" + tp.PcBangs + "</font></td>");
					html5.append("<td width=80 align=\"center\"><font color=" + color + ">" + OnOff + "</font></td>");
					html5.append("</tr>");
				}
				html5.append("</table>");
				
				String content = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/stats/stats_top_pcbangs.htm", player);
				
				content = content.replace("%stats_top_pc_bangs%", html5.toString());
				ShowBoard.separateAndSend(content, player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rs);
			}
			return;
		}
		else if (command.startsWith("_bbsstat_online"))
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM characters WHERE accesslevel = '0' ORDER BY onlinetime DESC LIMIT 100;");
				rs = statement.executeQuery();
				
				StringBuilder html6 = new StringBuilder();
				html6.append("<table width=440>");
				while (rs.next())
				{
					StatisticData tp = new StatisticData();
					tp.PlayerId = rs.getInt("obj_Id");
					tp.ChName = rs.getString("char_name");
					tp.ChSex = rs.getInt("sex");
					tp.ChGameTime = rs.getInt("onlinetime");
					tp.ChOnOff = rs.getInt("online");
					
					String sex = tp.ChSex == 1 ? "F" : "М";
					String color;
					String OnOff;
					if (tp.ChOnOff == 1)
					{
						OnOff = "Online.";
						color = "00CC00";
					}
					else
					{
						OnOff = "Offline.";
						color = "D70000";
					}
					html6.append("<tr>");
					html6.append("<td width=150 align=\"center\">" + tp.ChName + "</td>");
					html6.append("<td width=50 align=\"center\">" + sex + "</td>");
					html6.append("<td width=80 align=\"center\"><font color=00CC00>" + OnlineTime(tp.ChGameTime) + "</font></td>");
					html6.append("<td width=80 align=\"center\"><font color=" + color + ">" + OnOff + "</font></td>");
					html6.append("</tr>");
				}
				html6.append("</table>");
				
				String content = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/stats/stats_online.htm", player);
				content = content.replace("%stats_online%", html6.toString());
				ShowBoard.separateAndSend(content, player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rs);
			}
			return;
		}
		else if (command.startsWith("_bbsstat_clan"))
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT clan_subpledges.name,clan_data.clan_level,clan_data.reputation_score,clan_data.hasCastle,ally_data.ally_name FROM clan_data LEFT JOIN ally_data ON clan_data.ally_id = ally_data.ally_id, clan_subpledges WHERE clan_data.clan_level>0 AND clan_subpledges.clan_id=clan_data.clan_id AND clan_subpledges.type='0' order by clan_data.clan_level desc limit 100;");
				rs = statement.executeQuery();
				
				StringBuilder html7 = new StringBuilder();
				html7.append("<table width=440>");
				while (rs.next())
				{
					StatisticData tp = new StatisticData();
					tp.ClanName = rs.getString("name");
					tp.AllyName = rs.getString("ally_name");
					tp.ReputationClan = rs.getInt("reputation_score");
					tp.ClanLevel = rs.getInt("clan_level");
					tp.hasCastle = rs.getInt("hasCastle");
					String hasCastle = "";
					String castleColor = "D70000";
					
					switch (tp.hasCastle)
					{
						case 1:
							hasCastle = "Gludio";
							castleColor = "00CC00";
							break;
						case 2:
							hasCastle = "Dion";
							castleColor = "00CC00";
							break;
						case 3:
							hasCastle = "Giran";
							castleColor = "00CC00";
							break;
						case 4:
							hasCastle = "Oren";
							castleColor = "00CC00";
							break;
						case 5:
							hasCastle = "Aden";
							castleColor = "00CC00";
							break;
						case 6:
							hasCastle = "Innadril";
							castleColor = "00CC00";
							break;
						case 7:
							hasCastle = "Goddard";
							castleColor = "00CC00";
							break;
						case 8:
							hasCastle = "Rune";
							castleColor = "00CC00";
							break;
						case 9:
							hasCastle = "Schuttgart";
							castleColor = "00CC00";
							break;
						default:
							hasCastle = "No";
							castleColor = "D70000";
							break;
					}
					html7.append("<tr>");
					html7.append("<td width=140>" + tp.ClanName + "</td>");
					if (tp.AllyName != null)
					{
						html7.append("<td width=140>" + tp.AllyName + "</td>");
					}
					else
					{
						html7.append("<td width=140>No alliance</td>");
					}
					html7.append("<td width=100 align=\"center\">" + tp.ReputationClan + "</td>");
					html7.append("<td width=80 align=\"center\">" + tp.ClanLevel + "</td>");
					html7.append("<td width=100 align=\"center\"><font color=" + castleColor + ">" + hasCastle + "</font></td>");
					html7.append("</tr>");
				}
				html7.append("</table>");
				String content = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/stats/stats_clan.htm", player);
				content = content.replace("%stats_clan%", html7.toString());
				ShowBoard.separateAndSend(content, player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rs);
			}
			return;
		}
		else if (command.startsWith("_bbsstat_castle"))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT castle.name, castle.id, castle.tax_percent, castle.siege_date as siegeDate, clan_subpledges.name as clan_name, clan_data.clan_id " + "FROM castle " + "LEFT JOIN clan_data ON clan_data.hasCastle=castle.id " + "LEFT JOIN clan_subpledges ON clan_subpledges.clan_id=clan_data.clan_id AND clan_subpledges.type='0';");
				rs = statement.executeQuery();
				StringBuilder html8 = new StringBuilder();
				html8.append("<table width=460>");
				String color = "FFFFFF";
				
				while (rs.next())
				{
					StatisticData tp = new StatisticData();
					tp.Owner = rs.getString("clan_name");
					tp.NameCastl = rs.getString("name");
					tp.Percent = rs.getString("tax_Percent") + "%";
					tp.siegeDate = sdf.format(new Date(rs.getLong("siegeDate")));
					
					if (tp.Owner != null)
					{
						color = "00CC00";
					}
					else
					{
						color = "FFFFFF";
						tp.Owner = "no owner";
					}
					html8.append("<tr>");
					html8.append("<td width=160 align=\"center\">" + tp.NameCastl + "</td>");
					html8.append("<td width=110 align=\"center\">" + tp.Percent + "</td>");
					html8.append("<td width=200 align=\"center\"><font color=" + color + ">" + tp.Owner + "</font></td>");
					html8.append("<td width=160 align=\"center\">" + tp.siegeDate + "</td>");
					html8.append("</tr>");
				}
				html8.append("</table>");
				String content = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/stats/stats_castle.htm", player);
				content = content.replace("%stats_castle%", html8.toString());
				ShowBoard.separateAndSend(content, player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rs);
			}
			return;
		}
		else if (command.startsWith("_bbsservice_delevel"))
		{
			DelevelMe(player);
			return;
		}
		else if (command.startsWith("_bbsservice_col_to_psp"))
		{
			ChangeColToPsPFinish(player);
			return;
		}
		else if (command.startsWith("_bbsservice_col_to_psp_finish"))
		{
			ChangeColToPsPFinish(player);
			return;
		}
		ShowBoard.separateAndSend(html, player);
	}
	
	public void ChangeColToPsPFinish(Player player)
	{
		if (player == null)
		{
			return;
		}
		
		if ((player.getInventory().getCountOf(4037) < 5))
		{
			player.sendMessage("" + player.getName() + ", You do not have enough things to share");
			return;
		}
		
		removeItem(player, 4037, 5);
		int finPoint = (5 * 30);
		finPoint *= -1;
		player.reducePremiumPoints(finPoint);
		player.sendPacket(new ExBR_GamePoint(player));
		player.sendMessage("" + player.getName() + ", successfully added " + (5 * 30) + " balance ItemMall");
		player.sendChanges();
	}
	
	private void DelevelMe(Player player)
	{
		if (player.getLevel() <= 1) // TODO: config min level
		{
			player.sendMessage("This service is available to characters with " + 1 + " level.");
			return;
		}
		else if (getItemCount(player, 4037) < 100)// TODO: Config
		{
			player.sendMessage("You don't have enought items.");
			return;
		}
		else
		{
			long pXp = player.getExp();
			long tXp = Experience.LEVEL[(player.getLevel() - 1)];
			if (pXp <= tXp)
			{
				return;
			}
			removeItem(player, 4037, 100);// TODO: Config for remove item
			player.addExpAndSp(-(pXp - tXp), 0, 0, 0, false, false);
		}
	}
	
	public String OnlineTime(int time)
	{
		long onlinetimeH;
		int onlinetimeM;
		if (((time / 60 / 60) - 0.5) <= 0)
		{
			onlinetimeH = 0;
		}
		else
		{
			onlinetimeH = Math.round((time / 60 / 60) - 0.5);
		}
		onlinetimeM = Math.round(((time / 60 / 60) - onlinetimeH) * 60);
		return "" + onlinetimeH + " h. " + onlinetimeM + " m.";
	}
	
	@Override
	public void onWriteCommand(Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}
	
	@Override
	public void onLoad()
	{
		// Nacitani cb
		CommunityBoardManager.getInstance().registerHandler(this);
	}
	
	@Override
	public void onReload()
	{
		// reload cb
		CommunityBoardManager.getInstance().removeHandler(this);
	}
	
	@Override
	public void onShutdown()
	{
	}
}
