import java.util.ArrayList;
import java.util.List;

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

class Song{
    private String title;
    private String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "Song{title=" + title + ", artist=" + artist + "}";
    }
}

class MP3Player{
    private List<Song> songs;
    private Song currentSong;
    private State defaultState;
    private State playing;
    private State stopped;
    private State currentState;


    public MP3Player(List<Song> songs) {
        this.songs = songs;
        this.currentSong = songs.get(0);
        this.defaultState = new DefaultState(this);
        this.playing = new PlayingState(this);
        this.stopped = new StoppedState(this);
        this.currentState = defaultState;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getDefaultState() {
        return defaultState;
    }

    public State getPlaying() {
        return playing;
    }

    public State getStopped() {
        return stopped;
    }
    public void pressPlay(){
        currentState.pressPlay();
    }
    public void pressStop(){
        currentState.pressStop();
    }
    public void pressFWD(){
        currentState.pressFWD();
    }
    public void pressREW(){
        currentState.pressREW();
    }
    public void printCurrentSong(){
        System.out.println(currentSong.toString());
    }

    @Override
    public String toString() {
        return "MP3Player{currentSong = " +
                songs.indexOf(currentSong) +
                ", songList = " +
                songs +
                '}';
    }
}

interface State{
    public void pressPlay();
    public void pressStop();
    public void pressFWD();
    public void pressREW();
}

class DefaultState implements State{
    private MP3Player player;

    public DefaultState(MP3Player player) {
        this.player = player;
    }

    @Override
    public void pressPlay() {
        System.out.println("Song " + player.getSongs().indexOf(player.getCurrentSong()) + " is playing");
        player.setCurrentState(player.getPlaying());

    }

    @Override
    public void pressStop() {
        System.out.println("Songs are already stopped");
        //player.setCurrentSong(player.getSongs().get(0));
    }

    @Override
    public void pressFWD() {
        if(player.getCurrentSong().equals(player.getSongs().get(player.getSongs().size() - 1)))
            player.setCurrentSong(player.getSongs().get(0));
        else
            player.setCurrentSong(player.getSongs().get(player.getSongs().indexOf(player.getCurrentSong()) + 1));
        player.setCurrentState(player.getStopped());
        System.out.println("Forward...");
    }

    @Override
    public void pressREW() {
        if(player.getCurrentSong().equals(player.getSongs().get(0)))
            player.setCurrentSong(player.getSongs().get(player.getSongs().size() - 1));
        else
            player.setCurrentSong(player.getSongs().get(player.getSongs().indexOf(player.getCurrentSong()) - 1));
        player.setCurrentState(player.getStopped());
        System.out.println("Reward...");
    }
}
class PlayingState implements State{
    private MP3Player player;

    public PlayingState(MP3Player player) {
        this.player = player;
    }

    @Override
    public void pressPlay() {
        System.out.println("Song is already playing");
    }

    @Override
    public void pressStop() {
        System.out.println("Song " + player.getSongs().indexOf(player.getCurrentSong()) + " is paused");
//        player.setCurrentSong(player.getSongs().get(0));
        player.setCurrentState(player.getStopped());
    }

    @Override
    public void pressFWD() {
        if(player.getCurrentSong().equals(player.getSongs().get(player.getSongs().size() - 1)))
            player.setCurrentSong(player.getSongs().get(0));
        else
            player.setCurrentSong(player.getSongs().get(player.getSongs().indexOf(player.getCurrentSong()) + 1));
        player.setCurrentState(player.getStopped());
        System.out.println("Forward...");
    }

    @Override
    public void pressREW() {
        if(player.getCurrentSong().equals(player.getSongs().get(0)))
            player.setCurrentSong(player.getSongs().get(player.getSongs().size() - 1));
        else
            player.setCurrentSong(player.getSongs().get(player.getSongs().indexOf(player.getCurrentSong()) - 1));
        player.setCurrentState(player.getStopped());
        System.out.println("Reward...");
    }
}
class StoppedState implements State{
    private MP3Player player;

    public StoppedState(MP3Player player) {
        this.player = player;
    }

    @Override
    public void pressPlay() {
        System.out.println("Song " + player.getSongs().indexOf(player.getCurrentSong()) + " is playing");
        player.setCurrentState(player.getPlaying());
    }

    @Override
    public void pressStop() {
        System.out.println("Songs are stopped");
        player.setCurrentSong(player.getSongs().get(0));
        player.setCurrentState(player.getDefaultState());
    }

    @Override
    public void pressFWD() {
        if(player.getCurrentSong().equals(player.getSongs().get(player.getSongs().size() - 1)))
            player.setCurrentSong(player.getSongs().get(0));
        else
            player.setCurrentSong(player.getSongs().get(player.getSongs().indexOf(player.getCurrentSong()) + 1));
        player.setCurrentState(player.getStopped());
        System.out.println("Forward...");
    }

    @Override
    public void pressREW() {
        if(player.getCurrentSong().equals(player.getSongs().get(0)))
            player.setCurrentSong(player.getSongs().get(player.getSongs().size() - 1));
        else
            player.setCurrentSong(player.getSongs().get(player.getSongs().indexOf(player.getCurrentSong()) - 1));
        player.setCurrentState(player.getStopped());
        System.out.println("Reward...");
    }
}

