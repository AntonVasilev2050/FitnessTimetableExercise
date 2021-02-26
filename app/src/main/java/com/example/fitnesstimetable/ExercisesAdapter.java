package com.example.fitnesstimetable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExercisesViewHolder> {
    List<Exercise> exercises;

    public ExercisesAdapter(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.textViewName.setText(exercise.getName());
        holder.textViewStartTime.setText("с " + exercise.getStartTime());
        holder.textViewEndTime.setText("до " + exercise.getEndTime());
        holder.textViewTeacher.setText(exercise.getTeacher());
        holder.textViewPlace.setText(exercise.getPlace());
        holder.textViewDescription.setText(exercise.getDescription());
        holder.textViewWeekDay.setText(weekDayToString(exercise.getWeekDay()));
        holder.textViewAvailability.setText(availabilityToString(exercise.getAvailability()));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    private String weekDayToString (int weekDay){
        String[] week = {"", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
        return week[weekDay];
    }

    private String availabilityToString(int availability){
        if(availability <= 0){
            return "Мест нет";
        }else {
            return Integer.toString(availability);
        }
    }

    class ExercisesViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewStartTime;
        TextView textViewEndTime;
        TextView textViewTeacher;
        TextView textViewPlace;
        TextView textViewDescription;
        TextView textViewWeekDay;
        TextView textViewAvailability;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = itemView.findViewById(R.id.textViewEndTime);
            textViewTeacher = itemView.findViewById(R.id.textViewTeacher);
            textViewPlace = itemView.findViewById(R.id.textViewPlace);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewWeekDay = itemView.findViewById(R.id.textViewWeekDay);
            textViewAvailability = itemView.findViewById(R.id.textViewAvailability);
        }
    }
}
