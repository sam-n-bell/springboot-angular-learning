package com.app.sb_angular_backend.repository;

import com.app.sb_angular_backend.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {

    @Query(
            value = """
                    SELECT * FROM app.place
                    WHERE ST_DWithin(
                            location::geography,
                            ST_SetSRID(ST_Makepoint(:longitude, :latitude), 4326)::geography,
                            :kmDistance * 1000
                    )
                    -- <-> is a spatial operator for distance
                    ORDER BY location <-> ST_SetSRID(ST_Makepoint(:longitude, :latitude), 4326)
                    LIMIT :numberOfPlaces
                    """, nativeQuery = true
    )
    List<Place> findNearestPlaces(
            @Param("longitude") double longitude,
            @Param("latitude") double latitude,
            @Param("kmDistance") double kmDistance,
            @Param("numberOfPlaces") int numberOfResults
    );

}
