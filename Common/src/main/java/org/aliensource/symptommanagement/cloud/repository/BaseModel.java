package org.aliensource.symptommanagement.cloud.repository;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * Created by ttruong on 14-Nov-14.
 */
//@MappedSuperclass - https://docs.oracle.com/javaee/7/tutorial/doc/persistence-intro002.htm#BNBQP
//
// "... Mapped superclasses cannot be queried and cannot be used in EntityManager or Query operations.
//  You must use entity subclasses of the mapped superclass in EntityManager or Query operations.
//  Mapped superclasses can't be targets of entity relationships. Mapped superclasses can be abstract or concrete.
//  Mapped superclasses do not have any corresponding tables in the underlying datastore.
//  Entities that inherit from the mapped superclass define the table mappings.
//  For instance, in the preceding code sample, the underlying tables would be
//  FULLTIMEEMPLOYEE and PARTTIMEEMPLOYEE, but there is no EMPLOYEE table."
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//https://class.coursera.org/androidcapstone-001/forum/thread?thread_id=266#post-1250
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public abstract class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
