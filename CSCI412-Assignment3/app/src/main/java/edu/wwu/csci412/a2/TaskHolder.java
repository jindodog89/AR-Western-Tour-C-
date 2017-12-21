package edu.wwu.csci412.a2;


public class TaskHolder {
    private int id;
    private String taskInformation;
    private String duedate;
    private String name;
    private String email;

    public TaskHolder(int newId, String newtaskInformation, String newduedate, String newname, String newemail) {
        setId(newId);
        setTaskInformation(newtaskInformation);
        setDueDate(newduedate);
        setName(newname);
        setEmail(newemail);
    }

    public void setId( int newId ) { id = newId; }

    public void setTaskInformation(String newtaskInformation){ taskInformation = newtaskInformation; }

    public void setDueDate(String newduedate){
        duedate = newduedate;
    }

    public void setName(String newname){
        name = newname;
    }

    public void setEmail(String newemail){
        email = newemail;
    }


    public int getId() { return id; }

    public String getTaskInformation(){
        return taskInformation;
    }

    public String getDuedate(){
        return duedate;
    }

    public String getName(){ return name; }

    public String getEmail(){
        return email;
    }
}
