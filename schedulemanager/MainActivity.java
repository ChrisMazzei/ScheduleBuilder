package com.example.schedulemanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;

    EditText subject;
    EditText assignment;
    EditText status;
    EditText due;

    PopupMenu popupMenu;

    HashMap<Integer, String> subjectMap;
    HashMap<Integer, String> assignMap;
    HashMap<Integer, String> statusMap;
    HashMap<Integer, String> dueMap;

    int deleteID = -1;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        intent = new Intent(MainActivity.this, MainActivity.class);

        subjectMap = db.getSubjectMap();
        assignMap = db.getAssignmentMap();
        statusMap = db.getStatusMap();
        dueMap = db.getDueMap();

        //db.addSubject("ID IS ");
        //db.addID(69);

        //db.deleteSubject("ID IS ");
        //db.deleteIDs(69);

        FloatingActionButton addSubject = findViewById(R.id.add_subject);
        FloatingActionButton addRow = findViewById(R.id.add_row);
        final FloatingActionButton deleteRow = findViewById(R.id.delete_row);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText subjectInput = new EditText(this);

        final AlertDialog.Builder deleteRowBuilder = new AlertDialog.Builder(this);

        builder.setTitle("Enter Subject");
        deleteRowBuilder.setTitle("The selected row will be deleted");

        subjectInput.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(subjectInput);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string = subjectInput.getText().toString();
                popupMenu.getMenu().add(string);
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        deleteRowBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //click row to delete
                deleteRow();
                dialog.cancel();
            }
        });

        deleteRowBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

        addRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow();
            }
        });

        deleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRowBuilder.show();
            }
        });

        //Toast.makeText(MainActivity.this, db.getSub().keySet().toString(), Toast.LENGTH_LONG).show();

        if(!subjectMap.isEmpty() || !assignMap.isEmpty() || !statusMap.isEmpty() || !dueMap.isEmpty())
            recreateRows();
        else
            addRow();
    }

    private void recreateRows() {

        /**
         * TODO: CHECK THAT MAP IS NOT EMPTY, IF IT IS MAKE THE LENGTH 0 OR SUM SHIT
         */
        int subLen = -1;
        int assignLen = -1;
        int statLen = -1;
        int dueLen = -1;

        if(!subjectMap.isEmpty())
            subLen = rowLength(subjectMap);
        if(!assignMap.isEmpty())
            assignLen = rowLength(assignMap);
        if(!statusMap.isEmpty())
            statLen = rowLength(statusMap);
        if(!dueMap.isEmpty())
            dueLen = rowLength(dueMap);

        int arr[] = {subLen, assignLen, statLen, dueLen};
        int max = -1;

        for(int i=0; i<arr.length; i++)
            if(max < arr[i])
                max = arr[i];

        for(int i=0; i<=max; i++) {

            boolean subEmpty = false;
            boolean assignEmpty = false;
            boolean statEmpty = false;
            boolean dueEmpty = false;

            final TableLayout tableLayout = findViewById(R.id.tableLayout);
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            subject = new EditText(this);
            assignment = new EditText(this);
            status = new EditText(this);
            due = new EditText(this);

            if(subjectMap.containsKey(i))
                subject.setText(subjectMap.get(i));
            else {
                subject.setText("");
                subEmpty = true;
            }
            //subject.setTag(tableLayout.getChildCount());

            if(assignMap.containsKey(i))
                assignment.setText(assignMap.get(i));
            else {
                assignment.setText("");
                assignEmpty = true;
            }
            //assignment.setTag(tableLayout.getChildCount());

            if(statusMap.containsKey(i))
                status.setText(statusMap.get(i));
            else {
                status.setText("");
                statEmpty = true;
            }
            //status.setTag(tableLayout.getChildCount());

            if(dueMap.containsKey(i))
                due.setText(dueMap.get(i));
            else {
                due.setText("");
                dueEmpty = true;
            }
            //due.setTag(tableLayout.getChildCount());


            subject.setTag(tableLayout.getChildCount());
            assignment.setTag(tableLayout.getChildCount());
            status.setTag(tableLayout.getChildCount());
            due.setTag(tableLayout.getChildCount());

            tableRow.addView(subject);
            tableRow.addView(assignment);
            tableRow.addView(status);
            tableRow.addView(due);

            setListeners();

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            if(subEmpty && assignEmpty && statEmpty && dueEmpty)
            {
                tableRow.removeView(subject);
                tableRow.removeView(assignment);
                tableRow.removeView(status);
                tableRow.removeView(due);
            }
        }
    }

    private void addRow() {
        final TableLayout tableLayout = findViewById(R.id.tableLayout);
        final TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        subject = new EditText(this);
        assignment = new EditText(this);
        status = new EditText(this);
        due = new EditText(this);

        subject.setText("Subject");
        subject.setTag(tableLayout.getChildCount());

        assignment.setText("Assignment");
        assignment.setTag(tableLayout.getChildCount());

        status.setText("Status");
        status.setTag(tableLayout.getChildCount());

        due.setText("Due Date");
        due.setTag(tableLayout.getChildCount());

        tableRow.addView(subject);
        tableRow.addView(assignment);
        tableRow.addView(status);
        tableRow.addView(due);

        setListeners();

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
    }

    public void deleteRow() {
        //db.deleteSubject("ID IS ");
        //db.deleteIDs(69);

        db.deleteSubject(subjectMap.get(deleteID));
        db.deleteSubID(deleteID);
        subjectMap.remove(deleteID);

        db.deleteAssignment(assignMap.get(deleteID));
        db.deleteAssignmentID(deleteID);
        assignMap.remove(deleteID);

        db.deleteStatus(statusMap.get(deleteID));
        db.deleteStatusID(deleteID);
        statusMap.remove(deleteID);

        db.deleteDue(dueMap.get(deleteID));
        db.deleteDueID(deleteID);
        dueMap.remove(deleteID);

        startActivity(intent);
        //Toast.makeText(MainActivity.this, "tag is " + deleteID, Toast.LENGTH_SHORT).show();
    }

    private int rowLength(HashMap<Integer, String> map) {
        Comparator<Map.Entry<Integer, String>> comparator =
                new Comparator<Map.Entry<Integer, String>>() {
                    @Override
                    public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                };
        return Collections.max(map.entrySet(), comparator).getKey();
    }
    private void setListeners() {
        subject.setOnTouchListener(subjectListener(subject));
        assignment.setOnTouchListener(assignmentListener(assignment));
        status.setOnTouchListener(statusListener(status));
        due.setOnTouchListener(dueListener(due));
    }

    View.OnTouchListener subjectListener(final EditText editText) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                deleteID = (int) editText.getTag();

                if(editText.getText().toString().equals("Subject"))
                    editText.setText("");

                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            db.addSubject(editText.getText().toString());
                            db.addSubjectID((Integer) editText.getTag());
                        }
                        return true;
                    }
                });
                return false;
            }
        };
    }

    View.OnTouchListener assignmentListener(final EditText editText) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                deleteID = (int) editText.getTag();

                if(editText.getText().toString().equals("Assignment"))
                    editText.setText("");

                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            db.addAssignment(editText.getText().toString());
                            db.addAssignmentID((Integer) editText.getTag());
                        }
                        return true;
                    }
                });
                return false;
            }
        };
    }

    View.OnTouchListener statusListener(final EditText editText) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                deleteID = (int) editText.getTag();

                if(editText.getText().toString().equals("Status"))
                    editText.setText("");

                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            db.addStatus(editText.getText().toString());
                            db.addStatusID((Integer) editText.getTag());
                        }
                        return true;
                    }
                });
                return false;
            }
        };
    }

    View.OnTouchListener dueListener(final EditText editText) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                deleteID = (int) editText.getTag();

                if(editText.getText().toString().equals("Due Date"))
                    editText.setText("");

                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            db.addDue(editText.getText().toString());
                            db.addDueID((Integer) editText.getTag());
                        }
                        return true;
                    }
                });
                return false;
            }
        };
    }
}
