package Election_sys;

public class Candidate {
    private String name;
    private int votes;

    public Candidate(String name) {
        this.name = name;
        this.votes = 0;
    }

    public void receiveVote() {
        votes++;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }
}
