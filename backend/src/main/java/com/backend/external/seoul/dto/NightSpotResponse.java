package com.backend.external.seoul.dto;

import java.util.List;

// 2️⃣ viewNightSpot 내부
public record NightSpotResponse(
        List<NightSpotRow> row
) {}
