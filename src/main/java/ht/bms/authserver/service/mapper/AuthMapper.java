package ht.bms.authserver.service.mapper;

import ht.bms.authserver.domain.BmsAccount;
import ht.bms.authserver.model.AccountBean;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedTypeName;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper( AuthMapper.class );
    @Mapping(source = "email",target = "username",defaultValue ="")
    @Mapping(source = "bmsUser.userId",target = "userId",defaultValue ="")
    @Mapping(source = "bmsUser.institutionId",target = "institutionId",defaultValue ="")
    //@Mapping(source = java("account.bmsUser.officeId!=null"),target = "officeId")
    //@Mapping(source = "bmsUser.officeId!=null",target = "officeId",QualifiedName="")
    AccountBean toAccountDto(BmsAccount account);

    @Named("isOfficeExit")
    static BigDecimal isOfficeExit(BmsAccount entity) {
        if(entity.getBmsUser().getOfficeId()!=null)
           return entity.getBmsUser().getOfficeId();
        return null;
    }

}

