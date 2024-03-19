import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApplication extends JFrame implements ActionListener {
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JButton submitButton;
    private Timer timer;
    private int timeLeft;
    private int currentQuestionIndex;
    private int score;
    private QuizQuestion[] questions;

    public QuizApplication() {
        setTitle("Quiz Application");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sample quiz questions
        questions = new QuizQuestion[] {
                new QuizQuestion("Which programming language is known for its use in web development?",
                        new String[] { "JavaScript", "Python", "Java", "C#" }, "JavaScript"),
                new QuizQuestion("What does HTML stand for?",
                        new String[] { "Hypertext Markup Language", "Hyperlink and Text Markup Language",
                                "High-Level Text Markup Language", "Home Text Markup Language" },
                        "Hypertext Markup Language"),
                new QuizQuestion("Who is often referred to as the father of modern computing?",
                        new String[] { "Alan Turing", "Bill Gates", "Steve Jobs", "Tim Berners-Lee" }, "Alan Turing"),
                new QuizQuestion("What does CSS stand for?",
                        new String[] { "Cascading Style Sheets", "Computer Style Sheets", "Creative Style Sheets",
                                "Coded Style Sheets" },
                        "Cascading Style Sheets"),
                new QuizQuestion("Which data structure uses the Last In, First Out (LIFO) principle?",
                        new String[] { "Stack", "Queue", "Array", "Linked List" }, "Stack"),
                new QuizQuestion("What is the purpose of the 'git' version control system?",
                        new String[] { "Tracking changes in source code", "Creating graphical user interfaces",
                                "Running automated tests", "Managing databases" },
                        "Tracking changes in source code"),
                new QuizQuestion(
                        "Which of the following is a popular framework for building single-page web applications?",
                        new String[] { "React", "Angular", "Vue.js", "Ember.js" }, "React"),
                new QuizQuestion("What is the output of 3 + 5 * 2?", new String[] { "13", "16", "11", "10" }, "13"),
                new QuizQuestion("What does JVM stand for in the context of Java programming?",
                        new String[] { "Java Virtual Machine", "Just Very Much", "JavaScript Version Manager",
                                "Java Visual Module" },
                        "Java Virtual Machine"),
                new QuizQuestion("Which sorting algorithm has the worst-case time complexity of O(n^2)?",
                        new String[] { "Bubble Sort", "Merge Sort", "Quick Sort", "Insertion Sort" }, "Bubble Sort")// Add
                                                                                                                    // more
                                                                                                                    // questions
                                                                                                                    // as
                                                                                                                    // needed
        };

        questionLabel = new JLabel();
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsPanel.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton, BorderLayout.SOUTH);

        startQuiz();
    }

    private void startQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        displayQuestion(currentQuestionIndex);
        startTimer();
    }

    private void displayQuestion(int index) {
        if (index < questions.length) {
            QuizQuestion currentQuestion = questions[index];
            questionLabel.setText(currentQuestion.getQuestion());
            String[] options = currentQuestion.getOptions();
            for (int i = 0; i < options.length; i++) {
                optionButtons[i].setText(options[i]);
                optionButtons[i].setSelected(false);
            }
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        timeLeft = 15; // Set the initial time for each question (in seconds)
        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
            } else {
                timer.stop();
                displayNextQuestion();
            }
        });
        timer.start();
    }

    private void displayNextQuestion() {
        currentQuestionIndex++;
        displayQuestion(currentQuestionIndex);
        startTimer();
    }

    private void endQuiz() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Quiz ended. Your score: " + score);
        // Display summary of correct/incorrect answers if needed
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            QuizQuestion currentQuestion = questions[currentQuestionIndex];
            for (int i = 0; i < optionButtons.length; i++) {
                if (optionButtons[i].isSelected()) {
                    String selectedOption = optionButtons[i].getText();
                    if (currentQuestion.isCorrect(selectedOption)) {
                        score++;
                    }
                    break;
                }
            }
            displayNextQuestion();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApplication().setVisible(true));
    }
}

class QuizQuestion {
    private String question;
    private String[] options;
    private String correctAnswer;

    public QuizQuestion(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(String selectedOption) {
        return selectedOption.equals(correctAnswer);
    }
}
