package exercise;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Engine implements Runnable {
    private Scanner scanner;
    private Connection connection;

    public Engine(Connection connection) {
        this.scanner = new Scanner(System.in);
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
//            this.getVillainsNames();
//            this.getMinionNames();
//            this.addMinionExc();
//            this.changeTownNamesCasing();
//            this.removeVillainExc();
//            this.increaseMinionsAge();
            this.increaseAgeStoredProcedure();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * Problem 2
    * */
    private void getVillainsNames() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT v.name, COUNT(minion_id) as c\n" +
                "FROM  minions_villains mv\n" +
                "JOIN villains v ON mv.villain_id = v.id\n" +
                "GROUP BY v.name\n" +
                "HAVING COUNT(minion_id) > 15\n" +
                "ORDER BY c DESC ");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.printf("%s %s%n",
                    rs.getString("v.name"),
                    rs.getString("c"));
        }
    }

    /*
    * Problem 3
    * */
    private void getMinionNames() throws SQLException {
        try {
            this.connection.setAutoCommit(false);

            PreparedStatement statement = this.connection.prepareStatement("SELECT v.name, m.name, m.age\n" +
                    "FROM villains v\n" +
                    "JOIN minions_villains mv ON v.id = mv.villain_id\n" +
                    "JOIN minions m ON mv.minion_id = m.id\n" +
                    "WHERE v.id = ?");

            int villainID = Integer.parseInt(scanner.nextLine());
            statement.setInt(1, villainID);

            ResultSet rs = statement.executeQuery();

            boolean isFirst = true;
            int count = 1;

            if (!rs.next()) {
                throw new SQLException(String.format("No villain with ID %d exists in the database.", villainID));
            }

            while (rs.next()) {

                if (isFirst) {
                    System.out.println(String.format("Villain: %s", rs.getString("v.name")));

                    isFirst = false;
                }

                System.out.printf("%d. %s %s%n",
                        count++,
                        rs.getString("m.name"),
                        rs.getString("m.age"));
            }
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
        }
    }

    /*
    * Problem 4
    * */
    //TODO : Add town_id to minions
    private void addMinionExc() throws SQLException {
                String[] minionData = scanner.nextLine().split("\\s+");
        String[] villainData = scanner.nextLine().split("\\s+");

        PreparedStatement townCheck = connection.prepareStatement("SELECT * FROM towns t\n" +
                "WHERE name = ?");

        String townName = minionData[3];
        townCheck.setString(1, townName);

        ResultSet townResult = townCheck.executeQuery();

        if (!townResult.next()) {
            PreparedStatement addTown = connection.prepareStatement("INSERT INTO towns(`name`) VALUES (?)");
            addTown.setString(1, townName);
            addTown.execute();
            System.out.println(String.format("Town %s was added to the database.", townName));
        }

        PreparedStatement villainCheck = connection.prepareStatement("SELECT * FROM villains WHERE name = ?");

        String villainName = villainData[1];
        villainCheck.setString(1, villainName);
        ResultSet villainResult = villainCheck.executeQuery();

        if(!villainResult.next()){
            PreparedStatement addVillain = connection.prepareStatement("INSERT INTO villains (`name`,`evilness_factor`) VALUES (?,?)");
            addVillain.setString(1,villainName);
            addVillain.setString(2,"evil");
            addVillain.execute();
            System.out.println(String.format("Villain %s was added to the database.",villainName));
        }


        PreparedStatement minionCheck = connection.prepareStatement("SELECT * FROM minions WHERE name = ? AND age = ?");
        String minionName = minionData[1];
        int minionAge = Integer.parseInt(minionData[2]);

        minionCheck.setString(1,minionName);
        minionCheck.setInt(2,minionAge);

        ResultSet minionResult = minionCheck.executeQuery();

        if(minionResult.next()){
            PreparedStatement villainMinionCheck = connection.prepareStatement("SELECT * FROM minions_villains WHERE minion_id = ? AND villain_id = ?");

            int minionID = getMinionID(minionName,minionAge);
            int villainID = getVillainID(villainName);

            villainMinionCheck.setInt(1,minionID);
            villainMinionCheck.setInt(2,villainID);
            ResultSet villainMinionResult = villainMinionCheck.executeQuery();

            if(villainMinionResult.next()){
                System.out.println("There is already minion serving to this villain !");
                return;
            } else {
                addMinionToVillain(minionID,villainID);
            }

        } else {
            addMinion(minionName, minionAge);
            int minionID = getMinionID(minionName,minionAge);
            int villainID = getVillainID(villainName);
            addMinionToVillain(minionID, villainID);
        }

        System.out.println(String.format("Successfully added %s to be minion of %s",minionName,villainName));
    }

    private  void addMinionToVillain(int minionID, int villainID) throws SQLException {
        PreparedStatement addMinionToVillain = connection.prepareStatement("INSERT INTO minions_villains(`minion_id`,`villain_id`) VALUES (?,?)");
        addMinionToVillain.setInt(1,minionID);
        addMinionToVillain.setInt(2,villainID);
        addMinionToVillain.execute();
    }

    private  int getVillainID(String villainName) throws SQLException {
        PreparedStatement getVillain = connection.prepareStatement("SELECT id FROM villains WHERE name = ?");
        getVillain.setString(1,villainName);
        ResultSet rs = getVillain.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    private  int getMinionID(String minionName, int minionAge) throws SQLException {
        PreparedStatement getMinion = connection.prepareStatement("SELECT id FROM minions WHERE name = ? AND age = ?");
        getMinion.setString(1,minionName);
        getMinion.setInt(2,minionAge);
        ResultSet rs = getMinion.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    private void addMinion(String minionName, int minionAge) throws SQLException {
        PreparedStatement minionAdd = connection.prepareStatement("INSERT INTO minions (`name,`age`) VALUES (?,?)");
        minionAdd.setString(1,minionName);
        minionAdd.setInt(2,minionAge);
        minionAdd.execute();
    }

/*
* Problem 5
* */
    private void changeTownNamesCasing() throws SQLException {
        String townsStatementQuery = "SELECT `name` FROM towns \n" +
                "WHERE country = ?";

        PreparedStatement selectTownsStatement = this.connection
                .prepareStatement(townsStatementQuery);

        String countryName = this.scanner.nextLine();
        selectTownsStatement.setString(1,countryName);

        ResultSet townsResult = selectTownsStatement.executeQuery();

        if(!townsResult.next()){
            System.out.println("No town names were affected.");
            return;
        }

        String updateQuery = "UPDATE towns\n" +
                "SET name = UPPER(name)\n" +
                "WHERE country = ?";

        PreparedStatement updateTownsStatement = this.connection.prepareStatement(updateQuery);
        updateTownsStatement.setString(1,countryName);
        updateTownsStatement.execute();

        String getCountQuery = "SELECT COUNT(name) as counts\n" +
                "FROM towns\n" +
                "GROUP BY country\n" +
                "HAVING country = ?;";

        PreparedStatement getCountStatement = this.connection.prepareStatement(getCountQuery);
        getCountStatement.setString(1,countryName);

        ResultSet getCountResult = getCountStatement.executeQuery();

        getCountResult.next();
        int townCount = getCountResult.getInt("counts");

        ResultSet getUpdatedTowns = selectTownsStatement.executeQuery();

        System.out.printf("%d town names were affected", townCount).println();
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        while(getUpdatedTowns.next()){

            for (int i = townCount; i > 1; i--) {
                sb.append(getUpdatedTowns.getString("name")).append(", ");
                getUpdatedTowns.next();
            }
            sb.append(getUpdatedTowns.getString("name")).append("]");
        }
        System.out.println(sb.toString());
    }

/*
* Problem 6
* */
    private void removeVillainExc() throws SQLException {
        String villainCheckQuery = "SELECT * FROM villains\n" +
                "WHERE id = ?;";

        PreparedStatement villainCheckStatement = this.connection
                .prepareStatement(villainCheckQuery);

        int searchedVillainID = Integer.parseInt(this.scanner.nextLine());
        villainCheckStatement.setInt(1,searchedVillainID);

        ResultSet villainCheckResult = villainCheckStatement.executeQuery();

        if(!villainCheckResult.next()){
            System.out.println("No such villain was found");
            return;
        }

        String getVillainMinionsCountQuery = "SELECT COUNT(mv.minion_id) as counts\n" +
                "FROM minions_villains mv\n" +
                "JOIN villains v ON mv.villain_id = v.id\n" +
                "WHERE mv.villain_id = ?\n" +
                "GROUP BY mv.villain_id;";

        PreparedStatement getVillainMinionsCountStatement = this.connection
                .prepareStatement(getVillainMinionsCountQuery);

        getVillainMinionsCountStatement.setInt(1,searchedVillainID);

        ResultSet getVillainMinionsCountResult = getVillainMinionsCountStatement.executeQuery();
        getVillainMinionsCountResult.next();



        String deleteVillainMinionsQuery = "DELETE FROM minions_villains\n" +
                "WHERE villain_id = ?;";
        PreparedStatement deleteVillainMinionsStatement = this.connection
                .prepareStatement(deleteVillainMinionsQuery);
        deleteVillainMinionsStatement.setInt(1,searchedVillainID);
        deleteVillainMinionsStatement.execute();

        String deleteVillainQuery = "DELETE FROM villains WHERE id = ?";
        PreparedStatement deleteVillainStatement = this.connection
                .prepareStatement(deleteVillainQuery);
        deleteVillainStatement.setInt(1,searchedVillainID);
        deleteVillainStatement.execute();

        System.out.printf("%s was deleted",villainCheckResult.getString("name")).println();
        System.out.printf("%d minions released",getVillainMinionsCountResult.getInt("counts"));

    }
/*
* Problem 8
* */
    private void increaseMinionsAge() throws SQLException {
        int[] minionsID = Arrays.stream(this.scanner.nextLine()
                .split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int id : minionsID) {
            String searchMinionQuery = "SELECT * FROM minions WHERE id = ?";

            PreparedStatement searchMinionStatement = this.connection
                    .prepareStatement(searchMinionQuery);

            searchMinionStatement.setInt(1,id);
            ResultSet searchMinionResult = searchMinionStatement.executeQuery();


            if(!searchMinionResult.next()){
                System.out.println("There is no minion with this id in the database.");
                continue;
            }

            String updateMinionQuery = "UPDATE minions\n" +
                    "SET name = LOWER(name), age = age + 1\n" +
                    "WHERE id = ?;";

            PreparedStatement updateMinionStatement = this.connection.
                    prepareStatement(updateMinionQuery);

            updateMinionStatement.setInt(1,id);
            updateMinionStatement.execute();
        }

        String selectAllMinionsQuery = "SELECT * FROM minions";

        Statement selectAllMinionsStatement = this.connection.createStatement();

        ResultSet selectAllMinionsResult = selectAllMinionsStatement
                .executeQuery(selectAllMinionsQuery);

        while (selectAllMinionsResult.next()){
            System.out.printf("%s %d",
                    selectAllMinionsResult.getString("name"),
                    selectAllMinionsResult.getInt("age")).println();
        }
    }


/*
* Problem 9
* */
    private void increaseAgeStoredProcedure() throws SQLException {
        int minionID = Integer.parseInt(this.scanner.nextLine());

        CallableStatement callProcedure = this.connection
                .prepareCall("{?=CALL usp_get_older(?)}");

        callProcedure.registerOutParameter(1,Types.INTEGER);
        callProcedure.setInt(2,minionID);
        callProcedure.execute();

        PreparedStatement selectMinion = this.connection.prepareStatement("SELECT * FROM minions WHERE id = ?");
        selectMinion.setInt(1,minionID);

        ResultSet rs = selectMinion.executeQuery();

        while (rs.next()){
            System.out.printf("%s %d",
                    rs.getString("name"),
                    rs.getInt("age")).println();
        }

    }
















}
