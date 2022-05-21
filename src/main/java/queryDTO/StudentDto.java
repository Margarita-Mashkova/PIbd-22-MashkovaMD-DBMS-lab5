package queryDTO;

public class StudentDto {
    private String FIO;
    private String matrialStatus;
    private String hostelStatus;
    private Double averageBall;
    private int grants;
    private int groupeId;

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getMatrialStatus() {
        return matrialStatus;
    }

    public void setMatrialStatus(Boolean matrialStatus) {
        if(matrialStatus) {
            this.matrialStatus = "Состоит в браке";
        }
        else this.matrialStatus = "Не остоит в браке";
    }

    public String getHostelStatus() {
        return hostelStatus;
    }

    public void setHostelStatus(Boolean hostelStatus) {
        if(hostelStatus) {
            this.hostelStatus = "Проживает";
        }
        else this.hostelStatus = "Не проживает";
    }

    public Double getAverageBall() {
        return averageBall;
    }

    public void setAverageBall(Double averageBall) {
        this.averageBall = averageBall;
    }

    public int getGrants() {
        return grants;
    }

    public void setGrants(int grants) {
        this.grants = grants;
    }

    public int getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(int groupeId) {
        this.groupeId = groupeId;
    }

    @Override
    public String toString() {
        return " ФИО: " + FIO + "\n Семейное положение: " + matrialStatus
                + "\n Проживание в общежитии: " + hostelStatus + "\n Средний балл за сессию: " + averageBall
                + "\n Размер стипендии: " + grants + "\n Id группы: " + groupeId + "\n";
    }
}
