package se.lexicon.todo_it_api.model.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class PersonFormDto {

    @NotBlank(message = "this field is required")
    @Size(min = 2, message = "Need to contain at least 2 letters")
    private String firstName;
    @NotBlank(message = "this field is required")
    @Size(min = 2, message = "Need to contain at least 2 letters")
    private String lastName;
    @NotNull(message = "this field is required")
    @PastOrPresent(message = "Need to be in the past of present")
    private LocalDate birthDate;

    public PersonFormDto() {
    }

    public PersonFormDto(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
