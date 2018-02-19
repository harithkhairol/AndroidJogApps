package net.harithproperties.jogapps;

/**
 * Created by Pika on 4/2/2018.
 */

public class Jogging {

    String joggingid = null;

    String date = null;
    String time = null;
    String distance = null;
    String speed = null;
    String duration = null;





    public Jogging(String Sdate, String Stime, String Sdistance,
                   String Sspeed, String Sduration , String Sjoggingid) {

        super();


        this.date = Sdate;

        this.time = Stime;

        this.distance = Sdistance;

        this.speed = Sspeed;

        this.duration = Sduration;

        this.joggingid = Sjoggingid;

    }



    public String getJoggingid() {

        return joggingid;

    }


    public String getDate() {

        return date;

    }
    public void setDate(String Date2) {

        this.date = Date2;

    }

    public String getTime() {

        return time;

    }
    public void setTime(String Time2) {

        this.time = Time2;

    }

    public String getDistance() {

        return distance;

    }
    public void setDistance(String Distance2) {

        this.distance = Distance2;

    }

    public String getSpeed() {

        return speed;

    }
    public void setSpeed(String Speed2) {

        this.speed = Speed2;

    }

    public String getDuration() {

        return duration;

    }
    public void setDuration(String Duration2) {

        this.duration = Duration2;

    }


    @Override
    public String toString() {

        return  date + " " + time + " " + distance + " " + speed + " " + duration   ;

    }

}