package edu.tcu.cs.peerevalutationtool.system;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.admin.AdminRepository;
import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.team.TeamRepository;
import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.section.SectionRepository;
import edu.tcu.cs.peerevalutationtool.team.Team;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final SectionRepository sectionRepository;

    private final AdminRepository adminRepository;
    private final TeamRepository teamRepository;


    public DBDataInitializer(SectionRepository sectionRepository, AdminRepository adminRepository, TeamRepository teamRepository) {
        this.sectionRepository = sectionRepository;
        this.adminRepository = adminRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Section Initialization
        Section sec1 = new Section();
        sec1.setId("Section 2017-2018");
        sec1.setYear("2017-2018");
        sec1.setFirstDate("08/21/17");
        sec1.setLastDate("05/01/18");

        Section sec2 = new Section();
        sec2.setId("Section 2018-2019");
        sec2.setYear("2018-2019");
        sec2.setFirstDate("08/21/18");
        sec2.setLastDate("05/01/19");

        Section sec3 = new Section();
        sec3.setId("Section 2019-2020");
        sec3.setYear("2019-2020");
        sec3.setFirstDate("08/21/19");
        sec3.setLastDate("05/01/20");

        Section sec4 = new Section();
        sec4.setId("Section 2020-2021");
        sec4.setYear("2020-2021");
        sec4.setFirstDate("08/21/20");
        sec4.setLastDate("05/01/21");

        Section sec5 = new Section();
        sec5.setId("Section 2021-2022");
        sec5.setYear("2021-2022");
        sec5.setFirstDate("08/21/21");
        sec5.setLastDate("05/01/22");

        Section sec6 = new Section();
        sec6.setId("Section 2022-2023");
        sec6.setYear("2022-2023");
        sec6.setFirstDate("08/21/22");
        sec6.setLastDate("05/01/23");

        Section sec7 = new Section();
        sec7.setId("Section 2023-2024");
        sec7.setYear("2023-2024");
        sec7.setFirstDate("08/21/23");
        sec7.setLastDate("05/01/24");

        Section sec8 = new Section();
        sec8.setId("Section 2023-2024-02");
        sec8.setYear("2023-2024");
        sec8.setFirstDate("08/21/23");
        sec8.setLastDate("05/01/24");

        //Admin Initialization
        Admin adm1 = new Admin();
        adm1.setId(1);
        adm1.setName("Bingyang Wei");

        Admin adm2 = new Admin();
        adm2.setId(2);
        adm2.setName("Mr. Humpty Dumpty");


        //Instructor Initialization
        Instructor instructor = new Instructor();
        instructor.setId(1);
        instructor.setName("Test instructor");

        //Teams Initialization
        Team team1 = new Team();
        team1.setId("Team 1");
        team1.setAcademicYear("2023-24");
        team1.setOverseer(adm1);

        Team team2 = new Team();
        team2.setId("Team 2");
        team2.setAcademicYear("2020-21");
        team2.setOverseer(adm1);

        Team team3 = new Team();
        team3.setId("Team 3");
        team3.setAcademicYear("2023-24");
        team3.setOverseer(adm1);

        //Populating admin with sections
        adm1.addSection(sec2);
        adm1.addSection(sec3);
        adm1.addSection(sec4);
        adm1.addSection(sec5);
        adm1.addSection(sec6);
        adm1.addSection(sec7);
        adm1.addSection(sec8);

        //Populating admin with teams.
        adm1.addTeam(team1);
        adm2.addTeam(team2);
        //instructor.addTeam(team1);


        //Saving entities in H2 database using repository save.
        //Admin
        adminRepository.save(adm1);
        adminRepository.save(adm2);

        //Section
        sectionRepository.save(sec1);

        //Team
        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);
    }
}
