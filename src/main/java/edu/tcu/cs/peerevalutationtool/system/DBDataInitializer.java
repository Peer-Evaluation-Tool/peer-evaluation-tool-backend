package edu.tcu.cs.peerevalutationtool.system;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.admin.AdminRepository;
import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.section.SectionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final SectionRepository sectionRepository;

    private final AdminRepository adminRepository;


    public DBDataInitializer(SectionRepository sectionRepository, AdminRepository adminRepository) {
        this.sectionRepository = sectionRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Section sec1 = new Section();
        sec1.setId("Section 2017-2018");
        sec1.setYear("2017-2018");

        Section sec2 = new Section();
        sec2.setId("Section 2018-2019");
        sec2.setYear("2018-2019");

        Section sec3 = new Section();
        sec3.setId("Section 2019-2020");
        sec3.setYear("2019-2020");

        Section sec4 = new Section();
        sec4.setId("Section 2020-2021");
        sec4.setYear("2020-2021");

        Section sec5 = new Section();
        sec5.setId("Section 2021-2022");
        sec5.setYear("2021-2022");

        Section sec6 = new Section();
        sec6.setId("Section 2022-2023");
        sec6.setYear("2022-2023");

        Section sec7 = new Section();
        sec7.setId("Section 2023-2024");
        sec7.setYear("2023-2024");

        Section sec8 = new Section();
        sec8.setId("Section 2023-2024-02");
        sec8.setYear("2023-2024");

        Admin adm1 = new Admin();
        adm1.setId(1);
        adm1.setName("Bingyang Wei");
        adm1.addSection(sec2);
        adm1.addSection(sec3);
        adm1.addSection(sec4);
        adm1.addSection(sec5);
        adm1.addSection(sec6);
        adm1.addSection(sec7);
        adm1.addSection(sec8);

        adminRepository.save(adm1);

        sectionRepository.save(sec1);
    }
}
