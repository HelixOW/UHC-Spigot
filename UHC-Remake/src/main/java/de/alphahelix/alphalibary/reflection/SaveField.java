/*
 *     Copyright (C) <2016>  <AlphaHelixDev>
 *
 *     This program is free software: you can redistribute it under the
 *     terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphalibary.reflection;

import java.lang.reflect.Field;

public class SaveField {

    private Field f;

    public SaveField(Field f) {
        try {
            f.setAccessible(true);
            this.f = f;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object get(Object instance) {
        try {
            return f.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object get(Object instance, boolean stackTrace) {
        try {
            return f.get(instance);
        } catch (Exception e) {
            if (stackTrace) e.printStackTrace();
        }
        return null;
    }

    public void set(Object instance, Object value, boolean stackTrace) {
        try {
            f.set(instance, value);
        } catch (Exception e) {
            if (stackTrace) e.printStackTrace();
        }
    }
}
