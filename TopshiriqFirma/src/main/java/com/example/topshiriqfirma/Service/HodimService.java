package com.example.topshiriqfirma.Service;

import com.example.topshiriqfirma.Repository.HodimRepositori;
import com.example.topshiriqfirma.Repository.RollRepository;
import com.example.topshiriqfirma.Repository.VazifaRepository;
import com.example.topshiriqfirma.WebToken.GetToken;
import com.example.topshiriqfirma.dto.*;
import com.example.topshiriqfirma.enums.RollName;
import com.example.topshiriqfirma.model.Hodimlar;
import com.example.topshiriqfirma.model.Roles;
import com.example.topshiriqfirma.model.Vazifalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HodimService implements UserDetailsService {
    @Autowired
    VazifaRepository vazifaRepository;
    @Autowired
    GetToken getToken;
    @Autowired
    HodimRepositori hodimRepositori;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RollRepository rollRepository;

    public ApiResponse Directortayinlash(DirectorDto directorDto){
        try {
            Hodimlar hodimlar=new Hodimlar();
            hodimlar.setIsm(directorDto.getIsm());
            hodimlar.setFamiliya(directorDto.getFamiliya());
            hodimlar.setTelefon(directorDto.getTelefon());
            hodimlar.setEmail(directorDto.getEmail());
            hodimlar.setParol(passwordEncoder.encode(directorDto.getParol()));
            hodimlar.setRols(rollRepository.findByRollName(RollName.DIRECTOR));
            hodimlar.setType(true);
            hodimlar.setEnabled(true);
            hodimRepositori.save(hodimlar);
            return new ApiResponse("Direktor muvaffaqiyatli ro`yxatdan o`tdi",true);
        }
        catch (Exception ex){
            ex.getStackTrace();
            return new ApiResponse("Allaqachon ro`yxatdan o`tgan",true);
        }
    }
    public ApiResponse manegerTayinlash(HodimDto hodimDto){
        Optional<Hodimlar> optionalHodimlar = hodimRepositori.findById(hodimDto.getHodimId());
        Optional<Roles> optionalRoles = rollRepository.findById(hodimDto.getRolturi());
        if (optionalHodimlar.isPresent() && optionalHodimlar.get().isType()){
            if (optionalHodimlar.get().getRols().getRollName().equals(RollName.DIRECTOR) || optionalHodimlar.get().getRols().getRollName().equals(RollName.MANEGER)){
                Hodimlar hodimlar=new Hodimlar();
                hodimlar.setIsm(hodimDto.getIsm());
                hodimlar.setFamiliya(hodimDto.getFamiliya());
                hodimlar.setTelefon(hodimDto.getTelefon());
                hodimlar.setEmail(hodimDto.getEmail());
                hodimlar.setParol(passwordEncoder.encode(hodimDto.getParol()));
                hodimlar.setRols(rollRepository.findByRollName(RollName.DIRECTOR));
                hodimlar.setEmailcode(UUID.randomUUID().toString());
                if (optionalRoles.get().getRollName().equals(RollName.DIRECTOR)){
                    return new ApiResponse(optionalRoles.get().getRollName()+" Ro`yxatga qo'shishga ruxsat berilmagan"+ optionalHodimlar.get().getRols().getRollName().toString(),false);
                }
                hodimlar.setRols(rollRepository.findByRollName(optionalRoles.get().getRollName()));
                hodimlar.setType(false);
                boolean verification=emailVerification(hodimlar.getEmail(),hodimlar.getEmailcode());
                if (verification){
                    hodimRepositori.save(hodimlar);
                    return new ApiResponse("Xodim ro`yxatdan o`tdi, biz elektron pochtaga tasdiqlash kodini yuboramiz, iltimos, tasdiqlash kodini tasdiqlsng",true);
                }
                else return new ApiResponse("Mavjud emas!", false);
            }
            return new ApiResponse("Hodimni qo`shib bo`lmaydi!",false);
        }
        return new ApiResponse("Login mavjud emas!",false);
    }

    public List<Hodimlar> hodimlarniKorish(GetHodimDto getHodimDto){
        Optional<Roles> optionalRoles = rollRepository.findById(getHodimDto.getId());
        List<Hodimlar> hodimlarList1=hodimRepositori.findAll();
        List<Hodimlar> hodimlarList=new ArrayList<>();
        if (optionalRoles.get().getRollName().equals(RollName.DIRECTOR)){
            return hodimRepositori.findAll();
        }else if (optionalRoles.get().getRollName().equals(RollName.MANEGER)){
            for (Hodimlar i:hodimlarList1){
                if (i.getRols().getRollName().equals(RollName.USER)) hodimlarList.add(i);
            }
            return hodimlarList;
        }
        return null;
    }

    public ApiResponse ParolYangilash(LoginDto loginDto){
        Optional<Hodimlar> byEmail = hodimRepositori.findByEmail(loginDto.getLogin());
        if (byEmail.isPresent() && byEmail.get().getEnabled()){
            Hodimlar hodimlar=byEmail.get();
            hodimlar.setParol(passwordEncoder.encode(loginDto.getParol()));
            hodimRepositori.save(hodimlar);
            return new ApiResponse("Parol muvaffaqiyatli almashtirildi",true);
        }
        return new ApiResponse("Bunday foydalanuvchi mavjud emas",false);
    }

    public boolean emailVerification(String userEmail, String userCode){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("temuryoldoshev10@gmail.com");
            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Email tasdiqlsh");
            mailMessage.setText("<a href='http://localhost:8080/auth/emailConfirm?userCode="+userCode+"&userEmail="+userEmail+"'>Confirm email</a>");
            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception ex){
            ex.getStackTrace();
            return false;
        }
    }

    public ApiResponse emailConfirmation(String userEmail,String userCode){
        Optional<Hodimlar> optionalHodimlar = hodimRepositori.findByEmailAndEmailcode(userEmail, userCode);
        if (optionalHodimlar.isPresent()){
            Hodimlar hodimlar=optionalHodimlar.get();
            hodimlar.setEnabled(true);
            hodimlar.setEmailcode(null);
            hodimRepositori.save(hodimlar);
            String token=getToken.getToken("temuryoldoshev10@gmail.com",hodimlar.getRols());
            System.out.println(token);
            return new ApiResponse("Muvaffaqiyatli tasdiqlandi",true);
        }
        return new ApiResponse("Allaqachon faollashtirilgan",false);
    }

    public ApiResponse HodimLogin(LoginDto loginDto){
        try{
            Optional<Hodimlar> optionalHodimlar = hodimRepositori.findByEmail(loginDto.getLogin());
            if (optionalHodimlar.isPresent()){
                Optional<Vazifalar> vazifalarOptional = vazifaRepository.findByHodimlar(optionalHodimlar.get());
                String vazifaHaqida="";
                String vazifaNomi="Vazifa topilmadi";
                String vazifaTugashVaqti="";
                if (vazifalarOptional.isPresent()){
                    vazifaHaqida=vazifalarOptional.get().getVazifaHaqida();
                    vazifaNomi=vazifalarOptional.get().getVazifaNomi();
                    vazifaTugashVaqti=vazifalarOptional.get().getVazifaTugashVaqti();
                }
                Hodimlar hodimlar=optionalHodimlar.get();
                hodimlar.setType(true);
                hodimRepositori.save(hodimlar);
                Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLogin(),loginDto.getParol()));
                Hodimlar hodimlar1= (Hodimlar) authenticate.getPrincipal();
                return new ApiResponse("Profilingizga Xush kwlibsiz!!\n"+vazifaNomi+"\n"+vazifaHaqida+"\n"+vazifaTugashVaqti,true,getToken);
            }
            else {
                return new ApiResponse("Yaroqsiz login",false);
            }
        }
        catch (BadCredentialsException ex){
            ex.getStackTrace();
            return new ApiResponse("Login yoki parol xato!!",false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    public ApiResponse profildanChiqish(LoginDto loginDto){
        try {
            Optional<Hodimlar> optionalHodimlar = hodimRepositori.findByEmail(loginDto.getLogin());
            if (optionalHodimlar.isPresent()){
                Hodimlar hodimlar=optionalHodimlar.get();
                hodimlar.setType(false);
                hodimRepositori.save(hodimlar);
                return new ApiResponse("Profildan chiqildi!!", true);
            }
            else {
                return new ApiResponse("Yaroqsiz login",false);
            }
        }catch (BadCredentialsException e){
            e.getStackTrace();
            return new ApiResponse("Login yoki parol xato!!", false);
        }
    }
}
