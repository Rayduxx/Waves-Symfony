package tn.esprit.models;


import tn.esprit.controllers.ChordMakerController;
import tn.esprit.controllers.ChordSorterController;
import tn.esprit.controllers.PisteController;
import tn.esprit.controllers.PisteLayoutController;
import tn.esprit.objects.Accord;
import tn.esprit.objects.ExternInterface;
import tn.esprit.objects.MidiInterface;
import tn.esprit.objects.Tile;

import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainModel {
    public ChordModel chordModel;

    public ChordSorterController chordSorterController;
    public ChordMakerController chordMakerController;
    public PisteLayoutController pisteLayoutController;

    // Contient toutes les tracks
    public ArrayList<Track> applicationTracks;
    // Contient toute les "pistes"
    public ArrayList<PisteController> applicationPistes;

    // Permet de lier les tracks et les pistes
    public HashMap<PisteController, Track> pisteToTracks;


    public ExternInterface mainExternInterface;
    public MidiInterface midiInterface;

    public Accord selectedChord;
    public Tile selectedTile;

    public ArrayList<Integer> sink = new ArrayList<>();
    public LinkedHashMap<String, Integer> intrumentsMIDI;

    public MainModel()
    {
        selectedChord = null;

        this.applicationTracks = new ArrayList<>();
        this.applicationPistes = new ArrayList<>();


        this.intrumentsMIDI = new LinkedHashMap<String, Integer>();
        this.intrumentsMIDI.put("Piano",0);
        this.intrumentsMIDI.put("Guitar",25);
        this.intrumentsMIDI.put("Bass",33);
        this.intrumentsMIDI.put("Choir",52);
        this.intrumentsMIDI.put("Trumpet",56);
        this.intrumentsMIDI.put("Sax",64);
        this.intrumentsMIDI.put("Flute",73);
        this.intrumentsMIDI.put("Drum",114);
    }

    public Accord getSelectedChord()
    {
        return this.selectedChord.getClone();
    }
}
