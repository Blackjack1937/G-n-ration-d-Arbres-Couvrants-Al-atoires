package fr.univamu.graph;

public class Arc {
    private Edge support;
    private boolean reversed;

    /**
     * @param e        L'arête sur laquelle cet arc est basé.
     * @param reversed Détermine si l'arc est inversé (true) ou non (false).
     */
    public Arc(Edge e, boolean reversed) {
        this.support = e;
        this.reversed = reversed;
    }

    /**
     * @return Le sommet source de cet arc.
     */
    public int getSource() {
        return (reversed ? support.getDest() : support.getSource());
    }

    /**
     * @return Le sommet de destination de cet arc.
     */
    public int getDest() {
        return (reversed ? support.getSource() : support.getDest());
    }

    // Getters et setters pour support et reversed
    public Edge getSupport() { return this.support; }
    public void setSupport(Edge support) { this.support = support; }

    public boolean isReversed() { return this.reversed; }
    public void setReversed(boolean reversed) { this.reversed = reversed; }
}
