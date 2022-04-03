package com.company;

import java.util.ArrayList;

public class PasswordChecker {

    private String password;
    private boolean lowerCase;
    private boolean upperCase;
    private boolean digit;
    private int noSets;
    private int numberOfChanges;

    public PasswordChecker(String password) {
        this.password = password;
        this.lowerCase = false;
        this.upperCase = false;
        this.digit = false;
        this.noSets = 0;
        this.numberOfChanges = 0;
    }

    private void checkCharacters() {

        if (checkLowerCase()) {
            this.lowerCase = true;
        }
        if (checkUpperCase()) {
            this.upperCase = true;
        }
        if (checkDigit()) {
            this.digit = true;
        }
    }

    /*private String wrapper0(String password) {
        if (checkLength() == 6) {
            return solveLengthLess();
        }

        return password;
    }

    private String wrapper1(String password) {
        if (checkLength() == 20) {
            return solveLengthMore();
        }

        return password;
    }

    private String wrapper2(String password) {
        if (getPassword().length() == 6 || getPassword().length() == 20) {
            solveEqualityLength();
        }
        return password;
    }*/

    public int makeChanges() {

        String password = "";
        solveRepeatingCharacters();

        if (checkLength() == 6) {
            solveLengthLess();
        }

        if (checkLength() == 20) {
            solveLengthMore();
        }
        solveEqualityOrInLength();
        System.out.println(this.password);
        return this.numberOfChanges;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumberOfChanges() {
        return numberOfChanges;
    }

    public void setNumberOfChanges(int numberOfChanges) {
        this.numberOfChanges = numberOfChanges;
    }

    //check length of password out of bounds
    private int checkLength() {
        if (password.length() < 6) {
            //System.out.println("Password too short");
            return 6;
        }
        if (password.length() > 20) {
            //System.out.println("Password too long");
            return 20;
        }
        return 0;
    }

    private boolean checkLowerCase() {
        return this.password.matches(".*[a-z].*");
    }

    private boolean checkUpperCase() {
        return this.password.matches(".*[A-Z].*");
    }

    private boolean checkDigit() {
        return this.password.matches(".*\\d.*");
    }


    /*
        check if there are sequences of 3 characters that are equal and replace the one in the middle
        depending on the flags of lower case letter, upper case letter and digit
    */
    private void solveRepeatingCharacters() {
        checkCharacters();
        int consecutive = 0;
        for (int i = 2; i < password.length(); i++) {
            if (password.charAt(i) == password.charAt(i - 1) && password.charAt(i - 1) == password.charAt(i - 2)) {
                consecutive = 2;
            }
            if (consecutive == 2) {
                if (!this.lowerCase) {
                    addLowerCaseChar(i - 1);
                } else if (!this.upperCase) {
                    addUpperCaseChar(i - 1);
                } else if (!this.digit) {
                    addDigit(i - 1);
                } else {
                    addRandom(i - 1);
                }
                consecutive = 0;
                this.noSets++;
            }
        }
        System.out.println(noSets);
    }

    //if the length is less than 6 check for needed characters first and add them, else add random characters which do not repeat
    private void solveLengthLess() {
        if (!this.lowerCase) {
            addLowerCaseChar(this.password.length() - 1);
        }
        if (!this.upperCase) {
            addUpperCaseChar(this.password.length() - 1);
        }
        if (!this.digit) {
            addDigit(this.password.length() - 1);
        }
        while (checkLength() == 6) {
            addRandom(getPassword().length() - 1);
        }
    }

    /*solve the more than 20 characters problem
    create arrays of positions of characters to know if there are more than 1 characters of each type
    so that we can replace them or delete them if needed
    */
    private void solveLengthMore() {
        ArrayList<Integer> lowerPositions = new ArrayList<>();
        ArrayList<Integer> upperPositions = new ArrayList<>();
        ArrayList<Integer> digitPositions = new ArrayList<>();
        for (int i = 0; i < this.password.length(); i++) {
            if (Character.isLowerCase(this.password.charAt(i))) {
                lowerPositions.add(i);
            }
            if (Character.isUpperCase(this.password.charAt(i))) {
                upperPositions.add(i);
            }
            if (Character.isDigit(this.password.charAt(i))) {
                digitPositions.add(i);
            }
        }
        while (checkLength() == 20) {
            if (lowerPositions.size() > 1) {
                if (!this.upperCase) {
                    addUpperCaseChar(lowerPositions.get(0));
                    lowerPositions.remove(0);
                    System.out.println(this.password);
                }
                if (!this.digit) {
                    addDigit(lowerPositions.get(0));
                    lowerPositions.remove(0);
                    System.out.println(this.password);
                }
                remove(lowerPositions.get(1));
                System.out.println(this.password);
            }
            if (upperPositions.size() > 1) {
                if (!this.lowerCase) {
                    addLowerCaseChar(upperPositions.get(0));
                    upperPositions.remove(0);
                }
                if (!this.digit) {
                    addDigit(upperPositions.get(0));
                    upperPositions.remove(0);
                }
                remove(upperPositions.get(1));
            }
            if (digitPositions.size() > 1) {
                if (!this.lowerCase) {
                    addLowerCaseChar(digitPositions.get(0));
                    digitPositions.remove(0);
                }
                if (!this.upperCase) {
                    addUpperCaseChar(upperPositions.get(0));
                    digitPositions.remove(0);
                }
                remove(digitPositions.get(1));
            }
        }
    }


    /*solve the equality of bounds or length in bounds problem
    same idea of array of positions of characters which may be replaced if needed
    */
    private void solveEqualityOrInLength() {
        ArrayList<Integer> lowerPositions = new ArrayList<>();
        ArrayList<Integer> upperPositions = new ArrayList<>();
        ArrayList<Integer> digitPositions = new ArrayList<>();
        for (int i = 0; i < this.password.length(); i++) {
            if (Character.isLowerCase(this.password.charAt(i))) {
                lowerPositions.add(i);
            }
            if (Character.isUpperCase(this.password.charAt(i))) {
                upperPositions.add(i);
            }
            if (Character.isDigit(this.password.charAt(i))) {
                digitPositions.add(i);
            }
        }
        if (lowerPositions.size() > 1) {
            if (!this.upperCase) {
                addUpperCaseChar(lowerPositions.get(0));
                lowerPositions.remove(0);
                System.out.println(this.password);
            }
            if (!this.digit) {
                addDigit(lowerPositions.get(0));
                lowerPositions.remove(0);
                System.out.println(this.password);
            }
            System.out.println(this.password);
        }
        if (upperPositions.size() > 1) {
            if (!this.lowerCase) {
                addLowerCaseChar(upperPositions.get(0));
                upperPositions.remove(0);
            }
            if (!this.digit) {
                addDigit(upperPositions.get(0));
                upperPositions.remove(0);
            }
        }
        if (digitPositions.size() > 1) {
            if (!this.lowerCase) {
                addLowerCaseChar(digitPositions.get(0));
                digitPositions.remove(0);
            }
            if (!this.upperCase) {
                addUpperCaseChar(upperPositions.get(0));
                digitPositions.remove(0);
            }
        }
    }

    //add a random lower case letter
    private void addLowerCaseChar(int i) {
        char c = (char) ('a' + Math.random() * ('z' - 'a'));
        if (i == this.password.length() - 1) {
            this.password = this.password + c;
        } else {
            this.password = this.password.substring(0, i) + c + this.password.substring(i + 1);
        }
        this.lowerCase = true;
        this.numberOfChanges++;
    }

    //add a random upper case letter
    private void addUpperCaseChar(int i) {
        char c = (char) ('A' + Math.random() * ('Z' - 'A'));
        if (i == this.password.length() - 1) {
            this.password = this.password + c;
        } else {
            this.password = this.password.substring(0, i) + c + this.password.substring(i + 1);
        }
        this.upperCase = true;
        this.numberOfChanges++;
    }

    //add a random digit
    private void addDigit(int i) {
        int n = (int) (Math.random() * 10);
        char c = (char) (n + '0');
        if (i == this.password.length() - 1) {
            this.password = this.password + c;
        } else {
            this.password = this.password.substring(0, i) + c + this.password.substring(i + 1);
        }
        this.digit = true;
        this.numberOfChanges++;
    }

    //add a random ascii printable character different from the character in front of it
    private void addRandom(int i) {
        char c = (char) ('!' + Math.random() * ('~' - '!'));
        while (c == this.password.charAt(i - 1)) {
            c = (char) ('!' + Math.random() * ('~' - '!'));
        }
        if (i == this.password.length() - 1) {
            this.password = this.password + c;
        } else {
            while (c == this.password.charAt(i + 1)) {
                c = (char) ('!' + Math.random() * ('~' - '!'));
            }
            this.password = this.password.substring(0, i) + c + this.password.substring(i + 1);
        }
        this.numberOfChanges++;
    }

    //function to remove a character at a certain index
    private void remove(int i) {
        if (i != this.password.length() - 1) {
            this.password = this.password.substring(0, i) + this.password.substring(i + 1);
        } else {
            this.password = this.password.substring(0, i - 1);
        }
        this.numberOfChanges++;
    }

}
