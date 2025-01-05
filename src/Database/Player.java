package Database;

public class Player implements java.io.Serializable {
    private String name;
    private int age;
    private String country;
    private double height;
    private String Club;
    private String position;
    private int Number;
    private int weekly_salary;
    public Player(String name,int age,String country,double height,String Club,String position,int Number,int weekly_salary){
        this.name = name;
        this.age = age;
        this.country = country;
        this.height = height;
        this.Club = Club;
        this.position = position;
        this.Number = Number;
        this.weekly_salary = weekly_salary;
    }

    public String getName() {
        return name;
    }

    public String getClub() {
        return Club;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }
    public String getPosition(){
        return position;
    }

    public String getCountry() {
        return country;
    }
    public int getNumber() {
        return Number;
    }
    public int getWeekly_salary() {
        return weekly_salary;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setClub(String Club) {
        this.Club = Club;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setNumber(int number) {
        Number = number;
    }
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Player name: ").append(getName()).append("\n");
        sb.append("Player Country: ").append(getCountry()).append("\n");
        sb.append("Player Age: ").append(getAge()).append("\n");
        sb.append("Player Height: ").append(getHeight()).append("\n");
        sb.append("Player Club: ").append(getClub()).append("\n");
        sb.append("Player Position: ").append(getPosition()).append("\n");

        if (getNumber() == -1) {
            sb.append("Player Jersey Number: Not Available").append("\n");
        } else {
            sb.append("Player Jersey Number: ").append(getNumber()).append("\n");
        }

        sb.append("Weekly Salary: ").append(getWeekly_salary()).append("\n");

        return sb.toString();
        }

}