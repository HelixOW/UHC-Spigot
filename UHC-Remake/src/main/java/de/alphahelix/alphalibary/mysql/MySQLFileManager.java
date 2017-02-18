/*
 * Copyright (C) <2017>  <AlphaHelixDev>
 *
 *       This program is free software: you can redistribute it under the
 *       terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphalibary.mysql;

import de.alphahelix.alphalibary.AlphaPlugin;
import de.alphahelix.alphalibary.file.SimpleFile;

/**
 * Needs a rework
 */
class MySQLFileManager{

    private static AlphaPlugin plugin;

    public MySQLFileManager(AlphaPlugin plugin) {
        MySQLFileManager.plugin = plugin;
    }

    private SimpleFile<AlphaPlugin> getMySQLFile() {
        return new SimpleFile<>(MySQLAPI.getMySQLFilePath(), "mysql.ap", plugin);
    }

    public void setStandardMySQL() {
        SimpleFile<AlphaPlugin> cfg = getMySQLFile();

        cfg.setDefault("username", "root");
        cfg.setDefault("password", "password");
        cfg.setDefault("database", "localhost");
        cfg.setDefault("host", "localhost");
        cfg.setDefault("port", "3306");
    }

    public void readMySQL() {
        SimpleFile<AlphaPlugin> cfg = getMySQLFile();

        MySQLAPI.setMySQLConnection(cfg.getString("username"), cfg.getString("password"), cfg.getString("database"), cfg.getString("host"), cfg.getString("port"));
    }
}