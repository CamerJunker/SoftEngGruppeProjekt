    package projectmanagement;

    import java.util.ArrayList;

    import projectmanagement.Date;

    public class User {
        private String name;
        private ArrayList<Date> vacationDays;

        public User(String name) {
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
    }


    // class ProjectLeader extends User {
    // 
    // }

