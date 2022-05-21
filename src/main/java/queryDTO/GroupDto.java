package queryDTO;

public class GroupDto {
    private String groupName;
    private int cathedraId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getCathedraId() {
        return cathedraId;
    }

    public void setCathedraId(int cathedraId) {
        this.cathedraId = cathedraId;
    }

    @Override
    public String toString() {
        return " Наименование группы: " + groupName + "\n Id кафедры: " + cathedraId + "\n";
    }
}
