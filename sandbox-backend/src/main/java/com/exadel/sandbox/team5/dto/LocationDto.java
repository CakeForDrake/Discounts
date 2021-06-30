package com.exadel.sandbox.team5.dto;

import com.exadel.sandbox.team5.entity.Country;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LocationDto extends IdentifierDto {

    private Country country;
}
