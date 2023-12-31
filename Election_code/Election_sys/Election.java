package Election_sys;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Election {
    private ArrayList<Candidate> candidates;
    private Set<String> voters;
    private int totalVoters;

    public Election(int totalVoters) {
        this.candidates = new ArrayList<>();
        this.voters = new HashSet<>();
        this.totalVoters = totalVoters;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void conductElection() {
        Scanner scanner = new Scanner(System.in);
        while (voters.size() < totalVoters) {
            System.out.println("\n--- Election Voting System ---");
            System.out.println("Voter #" + (voters.size() + 1));
            System.out.println("Please enter your voter ID:");
            String voterId = scanner.nextLine();

            if (voters.contains(voterId)) {
                System.out.println("You have already voted! No duplicate votes allowed.");
                continue;
            }

            System.out.println("Welcome to the Election. Please vote for a candidate by entering their number:");
            for (int i = 0; i < candidates.size(); i++) {
                System.out.println((i + 1) + ". " + candidates.get(i).getName());
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over
            if (choice > 0 && choice <= candidates.size()) {
                candidates.get(choice - 1).receiveVote();
                voters.add(voterId);
                System.out.println("Thank you for voting!");
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public void displayResults() {
        System.out.println("\n--- Election Results ---");
        for (Candidate candidate : candidates) {
            System.out.println(candidate.getName() + ": " + candidate.getVotes() + " votes");
        }
    }
}
