public class main {
    public static void main(String[] args) {
        Election election = new Election(10); //There will be at least 10 voters. (open to changes).
        election.addCandidate(new Candidate("Alice"));
        election.addCandidate(new Candidate("Bob"));
        election.addCandidate(new Candidate("Charlie"));

        election.conductElection();
        election.displayResults();
    }
}
