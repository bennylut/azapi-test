package bgu.dcr.az.lab.data;
// Generated 21:13:44 25/05/2012 by Hibernate Tools 3.2.1.GA



/**
 * Experiments generated by hbm2java
 */
public class Experiments  implements java.io.Serializable {


     private Integer id;
     private byte[] content;

    public Experiments() {
    }

    public Experiments(byte[] content) {
       this.content = content;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public byte[] getContent() {
        return this.content;
    }
    
    public void setContent(byte[] content) {
        this.content = content;
    }




}


