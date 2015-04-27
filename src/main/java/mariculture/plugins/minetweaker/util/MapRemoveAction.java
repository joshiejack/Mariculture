package mariculture.plugins.minetweaker.util;

import java.util.Map;

import minetweaker.IUndoableAction;

public abstract class MapRemoveAction implements IUndoableAction {
    private final Map map;
    private final Object key;
    private Object value;

    public MapRemoveAction(Map map, Object key) {
        this.map = map;
        this.key = key;
        this.value = value;
    }

    @Override
    public void apply() {
        value = map.get(key);
        map.remove(key);
    }

    @Override
    public void undo() {
        map.put(key, value);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
