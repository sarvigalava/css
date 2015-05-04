package ro.info.uaic.structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lotus on 04.05.2015.
 */
public class Record implements Serializable
{
    private List<Cell> cells = new ArrayList<>();

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
