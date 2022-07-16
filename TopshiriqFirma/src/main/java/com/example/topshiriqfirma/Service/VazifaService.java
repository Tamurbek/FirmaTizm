package com.example.topshiriqfirma.Service;

import com.example.topshiriqfirma.Repository.HodimRepositori;
import com.example.topshiriqfirma.Repository.RollRepository;
import com.example.topshiriqfirma.Repository.VazifaRepository;
import com.example.topshiriqfirma.dto.ApiResponse;
import com.example.topshiriqfirma.dto.VazifaDto;
import com.example.topshiriqfirma.enums.RollName;
import com.example.topshiriqfirma.model.Hodimlar;
import com.example.topshiriqfirma.model.Roles;
import com.example.topshiriqfirma.model.Vazifalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VazifaService {
    @Autowired
    VazifaRepository vazifaRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    HodimRepositori hodimRepositori;
    @Autowired
    RollRepository roleRepository;

    public ApiResponse vazifaBerish(VazifaDto vazifaDto){
        Optional<Hodimlar> optionalHodimlar = hodimRepositori.findById(vazifaDto.getHosimId());
        Optional<Roles> optionalRoles = roleRepository.findById(vazifaDto.getBeruvchi());
        if (optionalHodimlar.isPresent()){
            if (optionalRoles.get().getRollName().equals(RollName.DIRECTOR) || optionalRoles.get().getRollName().equals(RollName.MANEGER)){
                Vazifalar vazifalar=new Vazifalar();
                vazifalar.setVazifaNomi(vazifaDto.getVazifaNomi());
                vazifalar.setVazifaHaqida(vazifaDto.getVazifaHaqida());
                vazifalar.setVazifaTugashVaqti(vazifaDto.getVazifaTugashVaqti());
                vazifalar.setHodimlar(optionalHodimlar.get());
                if (optionalRoles.get().getRollName().equals(RollName.DIRECTOR) && !optionalHodimlar.get().getRols().equals(RollName.DIRECTOR) || optionalRoles.get().getRollName().equals(RollName.MANEGER) && (!optionalHodimlar.get().getRols().equals(RollName.DIRECTOR) && !optionalHodimlar.get().getRols().equals(RollName.MANEGER))){
                    vazifaRepository.save(vazifalar);
                    emailVerification(optionalRoles.get().getRollName().toString(), optionalHodimlar.get().getEmail(), vazifaDto.getVazifaNomi(), vazifaDto.getVazifaHaqida());
                    return new ApiResponse("Vazifa muvaffaqiyatli yuborildi", true);
                }
                return new ApiResponse(optionalRoles.get().getRollName()+" Sizga vazifa qoʻshishga ruxsat yoʻq!",false);
            }
            return new ApiResponse("Qo`shilmadi!", false);
        }
        return new ApiResponse("Xodim Id si emas", false);
    }

    public ApiResponse readTask(Integer id){
        Optional<Hodimlar> optionalStaff = hodimRepositori.findById(id);
        if (optionalStaff.isPresent()){
            Optional<Vazifalar> optionalTasks=vazifaRepository.findByHodimlar(optionalStaff.get());
            return optionalTasks.map(tasks -> new ApiResponse(tasks.getVazifaNomi() + "\n" + tasks.getVazifaHaqida() + "\n" + tasks.getVazifaTugashVaqti(), true)).orElseGet(() -> new ApiResponse("this staff has no tasks", false));
        }
        return new ApiResponse("staff not found", false);
    }

    public boolean emailVerification(String fromEmail, String userEmail,String taskName, String taskInfo){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Task: "+taskName);
            mailMessage.setText(taskInfo);
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
