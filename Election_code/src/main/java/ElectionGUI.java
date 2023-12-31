import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class ElectionGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel statusLabel;
    private ArrayList<Candidate> candidates;
    private int totalVoters;
    private int votedCount;
    private JTextField voterIdField;

    public ElectionGUI(ArrayList<Candidate> candidates, int totalVoters) {
        this.candidates = candidates;
        this.totalVoters = totalVoters;
        this.votedCount = 0;
        initialize();
    }
    private void showResults() {
        JFrame chartFrame = new JFrame("Election Results");
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Candidate candidate : candidates) {
            dataset.setValue(candidate.getName(), candidate.getVotes());
        }

        JFreeChart chart = ChartFactory.createPieChart("Election Results", dataset, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));

        chartFrame.getContentPane().add(chartPanel);
        chartFrame.pack();
        chartFrame.setVisible(true);

        Candidate winner = findWinner();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chartFrame.dispose();
                showWinner(winner);
            }
        });

        chartFrame.getContentPane().add(nextButton, BorderLayout.SOUTH);
    }

    private Candidate findWinner() {
        Candidate winner = null;
        int maxVotes = 0;
        for (Candidate candidate : candidates) {
            if (candidate.getVotes() > maxVotes) {
                maxVotes = candidate.getVotes();
                winner = candidate;
            }
        }
        return winner;
    }

    private void showWinner(Candidate winner) {
        JFrame winnerFrame = new JFrame("Winner Announcement");
        winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winnerFrame.setSize(400, 200);

        JLabel winnerLabel = new JLabel("Congratulations to " + winner.getName() + "!", JLabel.CENTER);
        winnerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        winnerLabel.setForeground(Color.GREEN);
        winnerFrame.add(winnerLabel);

        winnerFrame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Election Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        statusLabel = new JLabel("Enter Voter ID and Select a Candidate");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        voterIdField = new JTextField();
        voterIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, voterIdField.getPreferredSize().height));

        panel.add(statusLabel);
        panel.add(voterIdField);

        for (int i = 0; i < candidates.size(); i++) {
            JButton button = new JButton(candidates.get(i).getName());
            button.addActionListener(new VoteActionListener(i));
            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class VoteActionListener implements ActionListener {
        private final int candidateIndex;

        public VoteActionListener(int candidateIndex) {
            this.candidateIndex = candidateIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (voterIdField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your Voter ID");
                return;
            }

            candidates.get(candidateIndex).receiveVote();
            votedCount++;
            statusLabel.setText("Thank you for voting! Votes cast: " + votedCount);

            if (votedCount >= totalVoters) {
                showResults();
            }
        }
    }

//    private void showResults() {
//        StringBuilder results = new StringBuilder("<html><body><h2>Election Results:</h2><ul>");
//        for (Candidate candidate : candidates) {
//            results.append("<li>").append(candidate.getName()).append(": ").append(candidate.getVotes()).append(" votes</li>");
//        }
//        results.append("</ul></body></html>");
//
//        JOptionPane.showMessageDialog(frame, results.toString());
//        System.exit(0);
//    }

    public static void main(String[] args) {
        ArrayList<Candidate> candidates = new ArrayList<>();
        candidates.add(new Candidate("Alice"));
        candidates.add(new Candidate("Bob"));
        candidates.add(new Candidate("Charlie"));

        new ElectionGUI(candidates, 10); // 10 voters
    }
}
