package flor_de_lotus.artigo.Event;

import flor_de_lotus.artigo.Artigo;

public class ArtigoCreatedEvent {
    private final Artigo artigo;

    public ArtigoCreatedEvent(Artigo artigo) {
        this.artigo = artigo;
    }

    public Artigo getArtigo() {
        return artigo;
    }
}
