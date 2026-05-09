    package projectmanagement;

    import java.util.ArrayList;
    import java.util.Locale;

    import projectmanagement.Date;

    public class User {
        private String name;
        private ArrayList<Date> vacationDays;

        public User(String name) {
            if (name == null || !name.matches("(?i)[a-z]{1,4}")) {
                throw new IllegalArgumentException("Employee initials must be 1 to 4 letters");
            }
            this.name = name;
            this.vacationDays = new ArrayList<>();
        }

        public String getName() {
            return this.name;
        }

        public ArrayList<Date> getVacationDates() {
            return this.vacationDays;
        }

        public void addVacationDate(Date date) throws Exception{
            for (Date vacationDate : this.vacationDays) {
                if (vacationDate.day == date.day && vacationDate.month == date.month && vacationDate.year == date.year) {
                    throw new Exception("Vacation day already registered");
                }
            }
            vacationDays.add(date);
        }
        
        public void removeVacationDate(Date date) throws Exception{
            for (Date vacationDate : this.vacationDays) {
                if (vacationDate.day == date.day && vacationDate.month == date.month && vacationDate.year == date.year) {
                    vacationDays.remove(vacationDate);
                    return;
                }
            }
            throw new Exception("Vacation day not registered");
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof User)) {
                return false;
            }
            User otherUser = (User) object;
            return this.name.equalsIgnoreCase(otherUser.name);
        }

        @Override
        public int hashCode() {
            return this.name.toLowerCase(Locale.ROOT).hashCode();
        }
    }


    // class ProjectLeader extends User {
    // 
    // }

