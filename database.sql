USE [APPSUCKHOE]
GO
/****** Object:  User [android]    Script Date: 12/31/2020 2:04:38 AM ******/
CREATE USER [android] FOR LOGIN [android] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [androidsuckhoe]    Script Date: 12/31/2020 2:04:38 AM ******/
CREATE USER [androidsuckhoe] WITHOUT LOGIN WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [apphealth]    Script Date: 12/31/2020 2:04:38 AM ******/
CREATE USER [apphealth] WITHOUT LOGIN WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [appsuckhoe]    Script Date: 12/31/2020 2:04:38 AM ******/
CREATE USER [appsuckhoe] WITHOUT LOGIN WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [androidsuckhoe]
GO
ALTER ROLE [db_accessadmin] ADD MEMBER [androidsuckhoe]
GO
ALTER ROLE [db_securityadmin] ADD MEMBER [androidsuckhoe]
GO
ALTER ROLE [db_ddladmin] ADD MEMBER [androidsuckhoe]
GO
ALTER ROLE [db_backupoperator] ADD MEMBER [androidsuckhoe]
GO
ALTER ROLE [db_datareader] ADD MEMBER [androidsuckhoe]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [androidsuckhoe]
GO
ALTER ROLE [db_denydatareader] ADD MEMBER [androidsuckhoe]
GO
ALTER ROLE [db_denydatawriter] ADD MEMBER [androidsuckhoe]
GO
/****** Object:  Table [dbo].[res_doctor]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[res_doctor](
	[did] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](max) NULL,
	[gender] [nvarchar](max) NULL,
	[birthday] [nvarchar](max) NULL,
	[age] [nvarchar](max) NULL,
	[email] [nvarchar](max) NULL,
	[address_id] [int] NULL,
	[faculty_id] [int] NULL,
	[uid] [int] NULL,
 CONSTRAINT [PK_res_doctor] PRIMARY KEY CLUSTERED 
(
	[did] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[res_doctor_GetAll]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[res_doctor_GetAll]()
RETURNS TABLE 
as
return
select did, name as fullName from res_doctor;
GO
/****** Object:  Table [dbo].[res_patient]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[res_patient](
	[pid] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](max) NULL,
	[age] [nvarchar](max) NULL,
	[address_id] [int] NULL,
	[disease_id] [int] NULL,
	[did] [int] NULL,
	[uid] [int] NULL,
	[phoneNumber] [nvarchar](max) NULL,
	[dateOfBirth] [nvarchar](max) NULL,
	[email] [nvarchar](max) NULL,
 CONSTRAINT [PK_res_patient] PRIMARY KEY CLUSTERED 
(
	[pid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[res_address]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[res_address](
	[aid] [int] IDENTITY(1,1) NOT NULL,
	[street] [nvarchar](max) NULL,
	[city] [nvarchar](max) NULL,
	[state] [nvarchar](max) NULL,
	[country] [nvarchar](max) NULL,
 CONSTRAINT [PK_res_address] PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[res_patient_GetById]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[res_patient_GetById]
(
	@id int
)
RETURNS TABLE 
as
return
select name as fullName, phoneNumber, dateOfBirth as DOB, email, t1.city, t1.state, t1.street, t1.country from res_patient t0
left join res_address t1 on t0.address_id = t1.aid
where uid = @id;
GO
/****** Object:  UserDefinedFunction [dbo].[res_doctor_GetbyId]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create FUNCTION [dbo].[res_doctor_GetbyId]
(
	@id nvarchar(255)
)
RETURNS table 
as
return
	select t2.name as fullName, t2.birthday as DOB
	from res_patient t1 join res_doctor t2 on t1.did = t2.did 
	where  t1.uid = @id;
GO
/****** Object:  UserDefinedFunction [dbo].[res_doctor_res_patients_GetAll]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[res_doctor_res_patients_GetAll]()
RETURNS TABLE 
as
return
select pid, name as fullName from res_patient;
GO
/****** Object:  UserDefinedFunction [dbo].[res_doctor_res_patients_GetId]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[res_doctor_res_patients_GetId]
(
	@id nvarchar(255)
)
RETURNS table 
as
return
	select t2.name as fullName, t2.dateOfBirth as DOB
	from res_doctor t1 join res_patient t2 on t1.did = t2.did 
	where  t1.uid = @id;
GO
/****** Object:  UserDefinedFunction [dbo].[res_doctor_res_patients_GetById]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[res_doctor_res_patients_GetById]
(
@id int
)
RETURNS TABLE 
as
return select pid,name as fullName from res_patient where dbo.res_patient.did = (select dbo.res_doctor.did from dbo.res_doctor where uid = @id);
GO
/****** Object:  Table [dbo].[res_appointment]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[res_appointment](
	[aid] [int] IDENTITY(1,1) NOT NULL,
	[date] [nvarchar](max) NULL,
	[status] [nvarchar](max) NULL,
	[patient_id] [int] NULL,
	[doctor_id] [int] NULL,
 CONSTRAINT [PK_inf_room] PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[res_doctor_res_Appointment]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[res_doctor_res_Appointment]
(
@id int --did of doctor
)
RETURNS TABLE 
as
return select res_appointment.date as DOH, res_patient.name as fullname 
from res_appointment join res_patient on res_appointment.patient_id = res_patient.pid
where doctor_id = (select dbo.res_doctor.did from dbo.res_doctor where uid = @id)
GO
/****** Object:  Table [dbo].[res_bill]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[res_bill](
	[bid] [int] IDENTITY(1,1) NOT NULL,
	[patient_id] [int] NULL,
	[no_of_days] [nvarchar](max) NULL,
	[total_price] [float] NULL,
	[real_pid] [int] NULL,
 CONSTRAINT [PK_res_bill] PRIMARY KEY CLUSTERED 
(
	[bid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[res_disease]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[res_disease](
	[did] [int] IDENTITY(1,1) NOT NULL,
	[symptom] [nvarchar](max) NULL,
	[treated] [nvarchar](max) NULL,
	[hospitalized_date] [nvarchar](max) NULL,
	[discharge_date] [nvarchar](max) NULL,
 CONSTRAINT [PK_res_disease] PRIMARY KEY CLUSTERED 
(
	[did] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[res_faculty]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[res_faculty](
	[fid] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](max) NOT NULL,
	[status] [nvarchar](max) NULL,
 CONSTRAINT [PK_res_faculty] PRIMARY KEY CLUSTERED 
(
	[fid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[res_user]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[res_user](
	[uid] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](max) NULL,
	[password] [nvarchar](max) NULL,
 CONSTRAINT [PK_res_user] PRIMARY KEY CLUSTERED 
(
	[uid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[res_appointment]  WITH CHECK ADD  CONSTRAINT [FK_res_appointment_res_doctor] FOREIGN KEY([doctor_id])
REFERENCES [dbo].[res_doctor] ([did])
GO
ALTER TABLE [dbo].[res_appointment] CHECK CONSTRAINT [FK_res_appointment_res_doctor]
GO
ALTER TABLE [dbo].[res_appointment]  WITH CHECK ADD  CONSTRAINT [FK_res_appointment_res_patient] FOREIGN KEY([patient_id])
REFERENCES [dbo].[res_patient] ([pid])
GO
ALTER TABLE [dbo].[res_appointment] CHECK CONSTRAINT [FK_res_appointment_res_patient]
GO
ALTER TABLE [dbo].[res_bill]  WITH CHECK ADD  CONSTRAINT [FK_res_bill_res_patient] FOREIGN KEY([patient_id])
REFERENCES [dbo].[res_patient] ([pid])
GO
ALTER TABLE [dbo].[res_bill] CHECK CONSTRAINT [FK_res_bill_res_patient]
GO
ALTER TABLE [dbo].[res_doctor]  WITH CHECK ADD  CONSTRAINT [FK_res_doctor_res_address] FOREIGN KEY([address_id])
REFERENCES [dbo].[res_address] ([aid])
GO
ALTER TABLE [dbo].[res_doctor] CHECK CONSTRAINT [FK_res_doctor_res_address]
GO
ALTER TABLE [dbo].[res_doctor]  WITH CHECK ADD  CONSTRAINT [FK_res_doctor_res_faculty] FOREIGN KEY([faculty_id])
REFERENCES [dbo].[res_faculty] ([fid])
GO
ALTER TABLE [dbo].[res_doctor] CHECK CONSTRAINT [FK_res_doctor_res_faculty]
GO
ALTER TABLE [dbo].[res_doctor]  WITH CHECK ADD  CONSTRAINT [FK_res_doctor_res_user] FOREIGN KEY([uid])
REFERENCES [dbo].[res_user] ([uid])
GO
ALTER TABLE [dbo].[res_doctor] CHECK CONSTRAINT [FK_res_doctor_res_user]
GO
ALTER TABLE [dbo].[res_patient]  WITH CHECK ADD  CONSTRAINT [FK_res_patient_res_address] FOREIGN KEY([address_id])
REFERENCES [dbo].[res_address] ([aid])
GO
ALTER TABLE [dbo].[res_patient] CHECK CONSTRAINT [FK_res_patient_res_address]
GO
ALTER TABLE [dbo].[res_patient]  WITH CHECK ADD  CONSTRAINT [FK_res_patient_res_disease] FOREIGN KEY([disease_id])
REFERENCES [dbo].[res_disease] ([did])
GO
ALTER TABLE [dbo].[res_patient] CHECK CONSTRAINT [FK_res_patient_res_disease]
GO
ALTER TABLE [dbo].[res_patient]  WITH CHECK ADD  CONSTRAINT [FK_res_patient_res_doctor] FOREIGN KEY([did])
REFERENCES [dbo].[res_doctor] ([did])
GO
ALTER TABLE [dbo].[res_patient] CHECK CONSTRAINT [FK_res_patient_res_doctor]
GO
ALTER TABLE [dbo].[res_patient]  WITH CHECK ADD  CONSTRAINT [FK_res_patient_res_user] FOREIGN KEY([uid])
REFERENCES [dbo].[res_user] ([uid])
GO
ALTER TABLE [dbo].[res_patient] CHECK CONSTRAINT [FK_res_patient_res_user]
GO
/****** Object:  StoredProcedure [dbo].[res_add_adrees]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

Create PROCEDURE [dbo].[res_add_adrees]
	@Stress nvarchar(255),
	@State nvarchar(255),
	@City nvarchar(255),
	@Country nvarchar(255)
AS
BEGIN
	begin transaction
	begin try
	INSERT dbo.res_address(street,state,city,country)
	VALUES	(@Stress,@State,@City,@Country);
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_add_fauculty]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

Create PROCEDURE [dbo].[res_add_fauculty]
	@name nvarchar(255),
	@status nvarchar(255)
AS
BEGIN
	begin transaction
	begin try
	INSERT dbo.res_faculty(name,status)
	VALUES	(@name,@status);
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_doctor_GetbyDoctorId]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[res_doctor_GetbyDoctorId]
	@Id nvarchar(255)
AS
BEGIN
	begin transaction
	begin try
		select name as fullName, birthday as DOB from res_doctor where did = @Id;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_doctor_PayBill]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_doctor_PayBill]
	@Id nvarchar(255)
AS
BEGIN
	declare @address_id int = 0;
	declare @disease_id int = 0;
	declare @uid int = 0;
	
	declare @total_price int = 0;
	declare @hospitalized_date nvarchar(255);
	declare @discharge_date nvarchar(255) = cast(DAY(GETDATE()) as nvarchar) + '/' + cast(MONTH(GETDATE()) as nvarchar) + '/' + cast(YEAR(GETDATE()) as nvarchar);
	declare @no_of_day int = 0;

	declare @Result int = 0;
	begin transaction
	begin try
		set @address_id = (select address_id from res_patient where pid = @Id);
		set @disease_id = (select disease_id from res_patient where pid = @Id);
		set @uid = (select uid from res_patient where pid = @Id);
		set @hospitalized_date = (select t2.hospitalized_date from res_patient t1 join res_disease t2 on t1.disease_id = t2.did where t1.pid = @Id);
		set @no_of_day = cast(DATEDIFF(DAY,convert(date,@hospitalized_date, 105),convert(date,@discharge_date, 105)) as int);

		Delete from res_appointment where patient_id = @Id;
		Delete from res_patient where pid = @Id;
		Delete from res_address where aid = @address_id;
		Delete from res_disease where did = @disease_id;
		Delete from res_user where uid = @uid;

		Insert into res_bill(patient_id,no_of_days,total_price,real_pid) values
		(NULL,@no_of_day,600*@no_of_day,@Id);

		set @Result = 1;
		select @Result as result;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_doctor_SaveChange]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_doctor_SaveChange]
	@name nvarchar(255),
	@gender nvarchar(255),
	@birthday nvarchar(255),
	@age nvarchar (255),
	@email nvarchar(255),
	@uid int
AS
BEGIN
	declare @address_id int;
	declare @fauculty_id int;
	declare @Resufl int = 0;
	begin transaction
	begin try
	Set @address_id =(select top 1 dbo.res_address.aid from dbo.res_address order by aid DESC)
	Set @fauculty_id = (select top 1 dbo.res_faculty.fid from dbo.res_faculty order by fid DESC)
	UPDATE dbo.res_doctor
	SET name=@name,gender=@gender,birthday=@birthday,age=@age,email=@email,address_id=@address_id,faculty_id=@fauculty_id
	WHERE dbo.res_doctor.uid = @uid;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_patient_AddDoctor]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_patient_AddDoctor]
	@Id nvarchar(255),
	@DoctorId nvarchar(255)
AS
BEGIN

	declare @Result int = 0;
	begin transaction
	begin try
		UPDATE res_patient
		SET did=@DoctorId
		WHERE uid = @Id;
		set @Result = 1;
		select @Result as result;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_patient_addSchedule]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_patient_addSchedule]
	@Id nvarchar(255),
	@Date nvarchar(255)
AS
BEGIN
	declare @patient_id int = 0;
	declare @doctor_id int = 0;
	declare @Result int = 0;
	begin transaction
	begin try
		Set @patient_id =(select pid from res_patient where uid = @Id);
		Set @doctor_id = (select did from res_patient where uid = @Id);
		insert into res_appointment(date,status,patient_id,doctor_id) values
		(@Date,NULL,@patient_id,@doctor_id);
		set @Result = 1;
		select @Result;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_patient_GetAppointment]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_patient_GetAppointment]
	@Id nvarchar(255)
AS
BEGIN

	declare @Result int = 0;
	begin transaction
	begin try
		Select * from res_appointment t1 join res_patient t2 on t1.patient_id = t2.pid where t2.uid = @Id;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_patient_getDisease]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[res_patient_getDisease]
	@Id int
AS
BEGIN
	begin transaction
	begin try
		select t1.symptom,t1.hospitalized_date,t1.treated from res_disease t1 join res_patient t2 on t1.did = t2.disease_id
		where t2.uid = @Id;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_patient_SaveChange]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_patient_SaveChange]
	@fullName nvarchar(255),
	@phoneNumber nvarchar(255),
	@DOB nvarchar(255),
	@email nvarchar(255),

	@city nvarchar(255),
	@state nvarchar(255),
	@street nvarchar(255),
	@country nvarchar(255),
	@id nvarchar(255)
AS
BEGIN
	declare @Result int = 0;
	begin transaction
	begin try

		exec res_add_adrees @street,@state,@city,@country;
		
		update res_patient set name = @fullName, phoneNumber = @phoneNumber,
		dateOfBirth = @DOB, email = @email, address_id = (select top 1 aid from res_address order by aid desc)
		where uid = @id;
		
		set @result = 1;
		select @Result;
		Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_patients_Adddissease]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[res_patients_Adddissease]
	@pid nvarchar (255),
	@Symptom nvarchar(255),
	@Treated nvarchar(255),
	@Hospitalized_date nvarchar(255),
	@Discharge_date nvarchar(255)
AS

BEGIN
	Declare @id_ds int;
	begin transaction
	begin try
	INSERT dbo.res_disease(symptom,treated,hospitalized_date,discharge_date)
	VALUES	(@Symptom,@Treated,@Hospitalized_date,@Discharge_date);
	Set @id_ds = (select top 1 dbo.res_disease.did from dbo.res_disease order by did DESC)
	UPDATE dbo.res_patient
	SET disease_id = @id_ds;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_Patients_GetbyPatientsId]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_Patients_GetbyPatientsId]
	@Id nvarchar(255)
AS
BEGIN
	begin transaction
	begin try
		select name as fullName, dateOfBirth as DOB from res_patient where pid = @Id;
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_user_login]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_user_login]
	@username nvarchar(255),
	@password nvarchar(255)
AS
BEGIN
	declare @Result int = 0;
	declare @Type nvarchar(max) = 'fail';
	declare @UID int = 0;
	begin transaction
	begin try
		
		if exists (select * from res_user t0 join res_patient t1 on t0.uid = t1.uid where t0.username = @username and t0.password = @password)
		begin
			set @Type = 'patient';
			set @UID = (select t0.uid from res_user t0 join res_patient t1 on t0.uid = t1.uid where t0.username = @username and t0.password = @password);
		end

		else if exists (select * from res_user t0 join res_doctor t1 on t0.uid = t1.uid where t0.username = @username and t0.password = @password)
		begin
			set @Type = 'doctor';
			set @UID = (select t0.uid from res_user t0 join res_doctor t1 on t0.uid = t1.uid where t0.username = @username and t0.password = @password);
		end
		
		
		if(@Type = 'doctor')
		begin
			set @Result = 1;
		end
		if (@Type = 'patient')
		begin
			set @Result = 2;
		end

		
		select @Result,@UID;
		Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
/****** Object:  StoredProcedure [dbo].[res_user_sign_up]    Script Date: 12/31/2020 2:04:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[res_user_sign_up]
	@username nvarchar(255),
	@password nvarchar(255),
	@isdoctor nvarchar(255)
AS
BEGIN
	declare @Result int = 0;
	begin transaction
	begin try
	if not exists ( Select * from dbo.res_user where username = @username)
	Begin
		INSERT dbo.res_user (username, password)
		VALUES	(@username, @password)
		if (@isdoctor = '1')
		Begin
		INSERT dbo.res_doctor(address_id,faculty_id,uid)
		VALUES	(null,null,(select dbo.res_user.uid from dbo.res_user where dbo.res_user.username = @username));
		End
		Else 
		Begin
		INSERT dbo.res_patient(address_id,disease_id,did,uid)
		VALUES	(null,null,null,(select dbo.res_user.uid from dbo.res_user where dbo.res_user.username = @username));
		End
		set @result = 1;
	End
	select @Result
	Commit;  
	end try
	begin catch
		Rollback;  
		Declare @Msg nvarchar(max);
		Select @Msg=Error_Message();
		RaisError('Error Occured: %s', 20, 101,@Msg) With Log;
	end catch
END
GO
