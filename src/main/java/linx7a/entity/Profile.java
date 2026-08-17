package linx7a.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "bio")
    private String bio;

    public Profile() {
    }

    public Profile(String phoneNumber, String address, String bio) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
