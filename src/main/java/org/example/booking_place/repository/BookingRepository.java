package org.example.booking_place.repository;

import org.example.booking_place.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.workspace.id = :workspaceId " +
            "AND NOT (b.endTime <= :startTime OR b.startTime >= :endTime)")
    List<Booking> findByWorkspaceIdAndTimeRange(
            @Param("workspaceId") Integer workspaceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    void deleteAllByWorkspace_Id(Integer workspaceId);


    List<Booking> findByWorkspace_IdAndStartTimeBefore(Integer workspaceId, LocalDateTime startTimeBefore);

    List<Booking> findByUser_IdAndStartTimeBefore(Integer userId, LocalDateTime startTimeBefore);

    List<Booking> findByStartTimeBefore(LocalDateTime startTimeBefore);

    List<Booking> findByWorkspace_IdAndStartTimeAfter(Integer workspaceId, LocalDateTime startTimeAfter);

    List<Booking> findByStartTimeAfter(LocalDateTime startTimeAfter);

    List<Booking> findByUser_IdAndStartTimeAfter(Integer userId, LocalDateTime startTimeAfter);
}
