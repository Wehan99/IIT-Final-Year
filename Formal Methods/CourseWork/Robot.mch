/* Robot
 * Author: User
 * Creation date: 1/5/2023
 */
MACHINE
    Robot
    

    
SEES
    Maze
    
SETS
    REPORT = {Robot_Exceeding_Maze_Boundary, Maze_Internal_Wall_Crashed, Robot_Moved_North, Robot_Moved_South, Robot_Moved_East, Robot_Moved_West, Cannot_Teleport_To_The_Same_Square, Robot_Teleported, Cannot_Teleport_In_The_Beginning, Yes, No}
    
VARIABLES
    robotXPosition,
    robotYPosition,
    robotCurrentPosition,
    visitedSquares
    
INVARIANT
   
   //robot x axis position in the maze's x axis range {1-7}
   robotXPosition : x_axis_range &
   
   //robot y axis position in the maze's y axis range {1-5}
   robotYPosition : y_axis_range &
   
   //current position is an element of the maze
   robotCurrentPosition : maze &
   
   //Squares that the robot has visited are saved as a sequence of the maze.
   visitedSquares : seq(maze)
   
INITIALISATION
     
   robotXPosition := 1 ||
   robotYPosition := 1 ||
   robotCurrentPosition := (1 |-> 1) ||
   visitedSquares := [(1 |-> 1)] //empty ekak dala balanna
   
OPERATIONS
    
    //Operation for move North
    
    report <-- MoveNorth =
        PRE report : REPORT
        THEN
            //Check whether robot exceeding the maze boundary
            IF(robotYPosition + 1 > max(y_axis_range))
            THEN
                report := Robot_Exceeding_Maze_Boundary
            //Check whether robot current position is a internal wall or not
            ELSIF ((robotXPosition |-> robotYPosition + 1) : internal_walls )
            THEN
                report := Maze_Internal_Wall_Crashed
            ELSE
                robotYPosition := robotYPosition + 1 ||
                robotCurrentPosition := (robotXPosition |-> robotYPosition + 1) ||
                visitedSquares := (visitedSquares <- (robotXPosition |-> robotYPosition + 1)) ||
                report := Robot_Moved_North
            END
        END;
       
       
    //Operation for move East
       
    report <-- MoveEast =
        PRE report : REPORT
        THEN
            //Check whether robot exceeding the maze boundary
            IF(robotXPosition + 1 > max(x_axis_range))
            THEN
                report := Robot_Exceeding_Maze_Boundary
            //Check whether robot current position is a internal wall or not
            ELSIF ((robotXPosition + 1 |-> robotYPosition) : internal_walls )
            THEN
                report := Maze_Internal_Wall_Crashed
            ELSE
                robotXPosition := robotXPosition + 1 ||
                robotCurrentPosition := (robotXPosition +1 |-> robotYPosition ) ||
                visitedSquares := (visitedSquares <- (robotXPosition +1 |-> robotYPosition )) ||
                report := Robot_Moved_East
            END
        END;
       
    //Operation for move South
    
    report <-- MoveSouth =
        PRE report : REPORT
        THEN
            //Check whether robot exceeding the maze boundary
            IF(robotYPosition - 1 > min(y_axis_range))
            THEN
                report := Robot_Exceeding_Maze_Boundary
            //Check whether robot current position is a internal wall or not
            ELSIF ((robotXPosition |-> robotYPosition - 1) : internal_walls )
            THEN
                report := Maze_Internal_Wall_Crashed
            ELSE
                robotYPosition := robotYPosition - 1 ||
                robotCurrentPosition := (robotXPosition |-> robotYPosition - 1) ||
                visitedSquares := (visitedSquares <- (robotXPosition |-> robotYPosition - 1)) ||
                report := Robot_Moved_South 
            END
        END;                
    
    //Operation for move West
       
    report <-- MoveWest =
        PRE report : REPORT
        THEN
            //Check whether robot exceeding the maze boundary
            IF(robotXPosition - 1 > min(x_axis_range))
            THEN
                report := Robot_Exceeding_Maze_Boundary
            //Check whether robot current position is a internal wall or not
            ELSIF ((robotXPosition - 1 |-> robotYPosition) : internal_walls )
            THEN
                report := Maze_Internal_Wall_Crashed
            ELSE
                robotXPosition := robotXPosition - 1 ||
                robotCurrentPosition := (robotXPosition - 1 |-> robotYPosition ) ||
                visitedSquares := (visitedSquares <- (robotXPosition - 1 |-> robotYPosition )) ||
                report := Robot_Moved_West
            END
        END;   
        
    //Operation for Teleport
    
    report <-- Teleport(teleportXPosition, teleportYPosition) =
        PRE report : REPORT & teleportXPosition : NATURAL1 & teleportYPosition : NATURAL1
        THEN
            //check robot has moved or not
            IF(size(visitedSquares) = 1)
            THEN
                report := Cannot_Teleport_In_The_Beginning
                
            //check robot does not teleport to the same square
            ELSIF(teleportXPosition = robotXPosition & teleportYPosition = robotYPosition )
            THEN
                report := Cannot_Teleport_To_The_Same_Square
            
            //check robot does not teleport to an internal wall
            ELSIF((teleportXPosition |-> teleportYPosition) : internal_walls )
            THEN
                report := Maze_Internal_Wall_Crashed
                
            //check robot does not teleport to outside the maze
            ELSIF((teleportXPosition |-> teleportYPosition) /: maze)
            THEN
                report := Robot_Exceeding_Maze_Boundary
                
            ELSE 
                robotCurrentPosition := (teleportXPosition |-> teleportYPosition ) ||
                visitedSquares := (visitedSquares <- (teleportXPosition |-> teleportYPosition )) ||
                report := Robot_Teleported
                
            END
        END;           
        
    //Get Position Operation
    
    position <-- getPosition =
        BEGIN
            position := robotCurrentPosition
        END;
    
    //Found exit Operation
    
    enquiry <-- foundExit =
        PRE enquiry : REPORT
        THEN
            //Check the robot has found the exit
            IF(robotCurrentPosition : exit_square)
            THEN
                enquiry := Yes
            ELSE
                enquiry := No
            END
        END;
    
    //visited Operation
    visited <-- visitedSquare(visitedXPosition, visitedYPosition ) =
        PRE visited : REPORT & visitedXPosition : NATURAL1 & visitedYPosition : NATURAL1
        THEN
            //check visited square coordinates are within the maze
            IF((visitedXPosition |-> visitedYPosition) : maze)
            THEN
            //check the robot has already visited the square
            
                IF((visitedXPosition |-> visitedYPosition) : ran(front(visitedSquares)))
                THEN
                    visited := Yes
                ELSE
                    visited := No
                END
                
            ELSE
                visited := Robot_Exceeding_Maze_Boundary
            END
        END;
        
    //Visited path Operation
    route <-- robotsRoute = 
        BEGIN
            route := visitedSquares
        END
        
    //Reset Operation
    
//   reset <-- ResetRobot =
//       PRE reset : REPORT
//       THEN
//       END
       

END
