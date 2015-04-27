package mariculture.plugins.minetweaker.util;

import java.util.Map;

import minetweaker.IUndoableAction;

public abstract class MapAddAction implements IUndoableAction {
    private final Map map;
    private final Object key;
    protected final Object value;

    public MapAddAction(Map map, Object key, Object value) {
        this.map = map;
        this.key = key;
        this.value = value;
    }

    @Override
    public void apply() {
        map.put(key, value);
    }

    @Override
    public void undo() {
        map.remove(key);
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
